package com.animora.season.repository;

import com.animora.anime.entity.Anime;
import com.animora.season.entity.Season;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SeasonRepository extends JpaRepository<Season, Long> {

    Page<Season> findByAnime_IdOrderBySeasonNumberAsc(Long animeId, Pageable pageable);

    boolean existsByAnimeAndSeasonNumber(Anime anime, int seasonNumber);

    boolean existsByAnime_Id(Long animeId);
}
