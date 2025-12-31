package com.animora.favorite.service.impl;

import com.animora.anime.entity.Anime;
import com.animora.anime.repository.AnimeRepository;
import com.animora.favorite.entity.Favorite;
import com.animora.favorite.repository.FavoriteRepository;
import com.animora.favorite.service.FavoriteService;
import com.animora.user.entity.User;
import com.animora.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, UserRepository userRepository, AnimeRepository animeRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.animeRepository = animeRepository;
    }

    @Override
    public Favorite addToFavorites(Long userId, Long animeId) {
        if (favoriteRepository.existsByUserIdAndAnimeId(userId, animeId)) {
            throw new IllegalStateException("Anime already in favorites");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new EntityNotFoundException("Anime not fond"));

        Favorite favorite = Favorite.builder()
                .user(user)
                .anime(anime)
                .createdAt(LocalDate.now())
                .build();

        return favoriteRepository.save(favorite);
    }

    @Override
    public void removeFromFavorites(Long userId, Long animeId) {
        Favorite favorite = favoriteRepository.findByUserIdAndAnimeId(userId, animeId)
                .orElseThrow(() -> new EntityNotFoundException("Favorite not found"));

        favoriteRepository.delete(favorite);
    }

    @Override
    public List<Favorite> getUserFavorites(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return favoriteRepository.findByUser(user);
    }
}
