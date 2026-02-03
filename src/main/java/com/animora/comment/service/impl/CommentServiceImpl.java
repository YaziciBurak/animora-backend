package com.animora.comment.service.impl;

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
import com.animora.comment.service.CommentService;
import com.animora.security.permission.PermissionType;
import com.animora.security.util.SecurityUtils;
import com.animora.user.entity.User;
import com.animora.user.exception.UserNotFoundException;
import com.animora.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AnimeRepository animeRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;


    @Override
    public CommentResponse createComment(Long animeId, CommentRequest request) {

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));

        User user = userRepository.findByEmail(SecurityUtils.currentUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Comment comment = commentMapper.toEntity(request, anime, user);

        Comment saved = commentRepository.save(comment);

        return commentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByAnime(Long animeId) {

        if (!animeRepository.existsById(animeId)) {
            throw new AnimeNotFoundException(animeId);
        }

        return commentRepository.findByAnimeId(animeId)
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                        .orElseThrow(() -> new CommentNotFoundException(commentId));

        boolean isOwner =
                comment.getUser().getEmail().equals(SecurityUtils.currentUserEmail());

        boolean canDelete =
                isOwner || SecurityUtils.hasPermission(PermissionType.COMMENT_DELETE);

        if (!canDelete) {
            throw new CommentDeleteForbiddenException(commentId);
        }

        commentRepository.delete(comment);
    }

}
