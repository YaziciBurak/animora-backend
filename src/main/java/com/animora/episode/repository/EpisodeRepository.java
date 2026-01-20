package com.animora.episode.repository;

import com.animora.episode.entity.Episode;
import com.animora.season.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode> findBySeasonOrderByEpisodeNumberAsc(Season season);

    boolean existsBySeasonIdAndEpisodeNumber(Long seasonId, int episodeNumber);
}
