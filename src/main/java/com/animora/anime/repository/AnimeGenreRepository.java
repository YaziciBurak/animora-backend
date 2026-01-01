package com.animora.anime.repository;

import com.animora.anime.entity.AnimeGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeGenreRepository extends JpaRepository<AnimeGenre, Long> {

    boolean existsByAnimeIdAndGenreId(Long animeId, Long genreId);
}
