package com.animora.favorite.service.impl;

import com.animora.anime.entity.Anime;
import com.animora.anime.exception.AnimeNotFoundException;
import com.animora.anime.repository.AnimeRepository;
import com.animora.favorite.dto.FavoriteResponse;
import com.animora.favorite.entity.Favorite;
import com.animora.favorite.exception.FavoriteAlreadyExistsException;
import com.animora.favorite.exception.FavoriteNotFoundException;
import com.animora.favorite.mapper.FavoriteMapper;
import com.animora.favorite.repository.FavoriteRepository;
import com.animora.favorite.service.FavoriteService;
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
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;
    private final FavoriteMapper favoriteMapper;


    @Override
    public FavoriteResponse createFavorite(Long userId, Long animeId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));

        if(favoriteRepository.existsByUserAndAnime(user, anime)) {
            throw new FavoriteAlreadyExistsException(userId, animeId);
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .anime(anime)
                .build();

        Favorite saved = favoriteRepository.save(favorite);

        return favoriteMapper.toResponse(saved);
    }

    @Override
    public void removeFavorite(Long userId, Long animeId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));

        Favorite favorite = favoriteRepository.findByUserAndAnime(user, anime)
                .orElseThrow(() -> new FavoriteNotFoundException(userId,animeId));

        favoriteRepository.delete(favorite);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FavoriteResponse> getUserFavorites(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return favoriteRepository.findByUser(user)
                .stream()
                .map(favoriteMapper::toResponse)
                .toList();
    }
}
