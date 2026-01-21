package com.animora.episode.service;

import com.animora.episode.dto.EpisodeRequest;
import com.animora.episode.dto.EpisodeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EpisodeService {

    EpisodeResponse createEpisodeToSeason(Long seasonId, EpisodeRequest request);

    Page<EpisodeResponse> getEpisodesBySeasonId(Long seasonId, Pageable pageable);
}
