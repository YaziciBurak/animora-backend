package com.animora.favorite.service;

import com.animora.anime.entity.Anime;
import com.animora.anime.exception.AnimeNotFoundException;
import com.animora.anime.repository.AnimeRepository;
import com.animora.favorite.dto.FavoriteResponse;
import com.animora.favorite.entity.Favorite;
import com.animora.favorite.exception.FavoriteAlreadyExistsException;
import com.animora.favorite.exception.FavoriteForbiddenException;
import com.animora.favorite.exception.FavoriteNotFoundException;
import com.animora.favorite.mapper.FavoriteMapper;
import com.animora.favorite.repository.FavoriteRepository;
import com.animora.favorite.service.impl.FavoriteServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceImplTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private FavoriteMapper favoriteMapper;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    private User user;
    private Anime anime;
    private Favorite favorite;
    private FavoriteResponse response;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(1L)
                .username("test")
                .build();

        anime = Anime.builder()
                .id(10L)
                .title("Naruto")
                .build();

        favorite = Favorite.builder()
                .user(user)
                .anime(anime)
                .build();

        response = FavoriteResponse.builder()
                .animeId(10L)
                .build();
    }

    @Test
    void createFavorite_success() {

        try (MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserId)
                    .thenReturn(1L);

            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            when(animeRepository.findById(10L)).thenReturn(Optional.of(anime));
            when(favoriteRepository.existsByUserAndAnime(user, anime)).thenReturn(false);
            when(favoriteRepository.save(any(Favorite.class))).thenReturn(favorite);
            when(favoriteMapper.toResponse(favorite)).thenReturn(response);

            FavoriteResponse result = favoriteService.createFavorite(10L);

            assertEquals(10L, result.getAnimeId());

            verify(favoriteRepository).save(any(Favorite.class));
        }
    }

    @Test
    void createFavorite_whenUserNotFound_throwException() {

        try (MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserId)
                    .thenReturn(1L);

            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class,
                    () -> favoriteService.createFavorite(10L));
        }
    }

    @Test
    void createFavorite_whenAnimeNotFound_throwException() {

        try (MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserId)
                    .thenReturn(1L);

            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            when(animeRepository.findById(10L)).thenReturn(Optional.empty());

            assertThrows(AnimeNotFoundException.class,
                    () -> favoriteService.createFavorite(10L));
        }
    }

    @Test
    void createFavorite_whenAlreadyExists_throwException() {

        try (MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserId)
                    .thenReturn(1L);

            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            when(animeRepository.findById(10L)).thenReturn(Optional.of(anime));
            when(favoriteRepository.existsByUserAndAnime(user, anime)).thenReturn(true);

            assertThrows(FavoriteAlreadyExistsException.class,
                    () -> favoriteService.createFavorite(10L));
        }
    }

    @Test
    void removeFavorite_success_whenOwner() {

        try (MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserId)
                    .thenReturn(1L);

            securityMock.when(() ->
                            SecurityUtils.hasPermission(PermissionType.FAVORITE_DELETE_ANY))
                    .thenReturn(false);

            when(favoriteRepository.findByUserIdAndAnimeId(1L, 10L))
                    .thenReturn(Optional.of(favorite));

            favoriteService.removeFavorite(10L);

            verify(favoriteRepository).delete(favorite);
        }
    }

    @Test
    void removeFavorite_success_whenAdmin() {

        try (MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserId)
                    .thenReturn(99L);

            securityMock.when(() ->
                            SecurityUtils.hasPermission(PermissionType.FAVORITE_DELETE_ANY))
                    .thenReturn(true);

            when(favoriteRepository.findByUserIdAndAnimeId(99L, 10L))
                    .thenReturn(Optional.of(favorite));

            favoriteService.removeFavorite(10L);

            verify(favoriteRepository).delete(favorite);
        }
    }

    @Test
    void removeFavorite_whenForbidden_throwException() {

        try (MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserId)
                    .thenReturn(99L);

            securityMock.when(() ->
                            SecurityUtils.hasPermission(PermissionType.FAVORITE_DELETE_ANY))
                    .thenReturn(false);

            when(favoriteRepository.findByUserIdAndAnimeId(99L, 10L))
                    .thenReturn(Optional.of(favorite));

            assertThrows(FavoriteForbiddenException.class,
                    () -> favoriteService.removeFavorite(10L));
        }
    }

    @Test
    void removeFavorite_whenNotFound_throwException() {

        try (MockedStatic<SecurityUtils> securityMock = mockStatic(SecurityUtils.class)) {

            securityMock.when(SecurityUtils::currentUserId)
                    .thenReturn(1L);

            when(favoriteRepository.findByUserIdAndAnimeId(1L, 10L))
                    .thenReturn(Optional.empty());

            assertThrows(FavoriteNotFoundException.class,
                    () -> favoriteService.removeFavorite(10L));
        }
    }

    @Test
    void getUserFavorites_success() {

        when(userRepository.existsById(1L)).thenReturn(true);
        when(favoriteRepository.findByUserId(1L)).thenReturn(List.of(favorite));
        when(favoriteMapper.toResponse(favorite)).thenReturn(response);

        List<FavoriteResponse> result = favoriteService.getUserFavorites(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getUserFavorites_whenUserNotFound_throwException() {

        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class,
                () -> favoriteService.getUserFavorites(1L));
    }
}

