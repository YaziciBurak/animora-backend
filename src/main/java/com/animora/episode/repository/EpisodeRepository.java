package com.animora.episode.repository;

import com.animora.episode.entity.Episode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    Page<Episode> findBySeasonId(Long seasonId, Pageable pageable);

    boolean existsBySeasonIdAndEpisodeNumber(Long seasonId, int episodeNumber);

    boolean existsBySeasonIdAndEpisodeNumberAndIdNot(Long seasonId, int episodeNumber, Long episodeId);
}
