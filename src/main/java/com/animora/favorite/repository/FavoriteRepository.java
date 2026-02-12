package com.animora.favorite.repository;

import com.animora.anime.entity.Anime;
import com.animora.favorite.entity.Favorite;
import com.animora.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserAndAnime(User user, Anime anime);

    Optional<Favorite> findByUserIdAndAnimeId(Long userId, Long animeId);

    List<Favorite> findByUserId(Long userId);
}
