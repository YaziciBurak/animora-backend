package com.animora.episode.service;

import com.animora.episode.dto.EpisodeRequest;
import com.animora.episode.dto.EpisodeResponse;

import java.util.List;

public interface EpisodeService {

    EpisodeResponse createEpisodeToSeason(Long seasonId, EpisodeRequest request);

    List<EpisodeResponse> getEpisodesBySeasonId(Long seasonId);
}
