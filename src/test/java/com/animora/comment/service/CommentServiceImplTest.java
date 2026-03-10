package com.animora.comment.service;

import com.animora.anime.entity.Anime;
import com.animora.anime.exception.AnimeNotFoundException;
import com.animora.anime.repository.AnimeRepository;
import com.animora.comment.dto.CommentRequest;
import com.animora.comment.dto.CommentResponse;
import com.animora.comment.entity.Comment;
import com.animora.comment.exception.CommentDeleteForbiddenException;
import com.animora.comment.exception.CommentNotFoundException;
import com.animora.comment.mapper.CommentMapper;
import com.animora.comment.repository.CommentRepository;
import com.animora.comment.service.impl.CommentServiceImpl;
import com.animora.security.permission.PermissionType;
import com.animora.security.util.SecurityUtils;
import com.animora.user.entity.User;
import com.animora.user.exception.UserNotFoundException;
import com.animora.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Anime anime;
    private User user;
    private Comment comment;
    private CommentRequest request;
    private CommentResponse response;

    @BeforeEach
    void setUp() {

        anime = Anime.builder()
                .id(1L)
                .title("Attack on Titan")
                .build();

        user = User.builder()
                .id(10L)
                .username("burak")
                .email("burak@test.com")
                .build();

        request = CommentRequest.builder()
                .content("Great anime")
                .build();

        comment = Comment.builder()
                .id(100L)
                .content("Great anime")
                .anime(anime)
                .user(user)
                .build();

        response = CommentResponse.builder()
                .id(100L)
                .content("Great anime")
                .userId(10L)
                .userName("burak")
                .build();
    }

    @Test
    void createComment_success() {
        try (MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserEmail)
                    .thenReturn("burak@test.com");

            when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
            when(userRepository.findByEmail("burak@test.com")).thenReturn(Optional.of(user));
            when(commentMapper.toEntity(request, anime, user)).thenReturn(comment);
            when(commentRepository.save(comment)).thenReturn(comment);
            when(commentMapper.toResponse(comment)).thenReturn(response);

            CommentResponse result = commentService.createComment(1L, request);

            assertEquals("Great anime", result.getContent());

            verify(commentRepository).save(comment);
        }
    }

    @Test
    void createComment_whenAnimeNotFound_throwException() {
        when(animeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AnimeNotFoundException.class,
                () -> commentService.createComment(1L, request));
    }

    @Test
    void createComment_whenUserNotFound_throwException() {
        try (MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserEmail)
                    .thenReturn("burak@test.com");

            when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
            when(userRepository.findByEmail("burak@test.com")).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class,
                    () -> commentService.createComment(1L, request));
        }
    }

    @Test
    void getCommentsByAnime_success() {
        when(animeRepository.existsById(1L)).thenReturn(true);
        when(commentRepository.findByAnimeId(1L)).thenReturn(List.of(comment));
        when(commentMapper.toResponse(comment)).thenReturn(response);

        List<CommentResponse> result = commentService.getCommentsByAnime(1L);

        assertEquals(1, result.size());
        assertEquals("Great anime", result.get(0).getContent());
    }

    @Test
    void getCommentsByAnime_whenAnimeNotFound_throwException() {
        when(animeRepository.existsById(1L)).thenReturn(false);

        assertThrows(AnimeNotFoundException.class,
                () -> commentService.getCommentsByAnime(1L));
    }

    @Test
    void deleteComment_success_whenOwner() {
        try(MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserId)
                    .thenReturn(10L);

            securityMock.when(() ->
                    SecurityUtils.hasPermission(PermissionType.COMMENT_DELETE_ANY))
                    .thenReturn(false);

            when(commentRepository.findById(100L)).thenReturn(Optional.of(comment));

            commentService.deleteComment(100L);

            verify(commentRepository).delete(comment);
        }
    }

    @Test
    void deleteComment_success_whenAdmin() {

        try (MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserId)
                    .thenReturn(99L);

            securityMock.when(() ->
                            SecurityUtils.hasPermission(PermissionType.COMMENT_DELETE_ANY))
                    .thenReturn(true);

            when(commentRepository.findById(100L)).thenReturn(Optional.of(comment));

            commentService.deleteComment(100L);

            verify(commentRepository).delete(comment);
        }
    }

    @Test
    void deleteComment_whenForbidden_throwException() {

        try (MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserId)
                    .thenReturn(99L);

            securityMock.when(() ->
                            SecurityUtils.hasPermission(PermissionType.COMMENT_DELETE_ANY))
                    .thenReturn(false);

            when(commentRepository.findById(100L)).thenReturn(Optional.of(comment));

            assertThrows(CommentDeleteForbiddenException.class,
                    () -> commentService.deleteComment(100L));
        }
    }

    @Test
    void deleteComment_whenCommentNotFound_throwException() {
        when(commentRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class,
                () -> commentService.deleteComment(100L));
    }
}
