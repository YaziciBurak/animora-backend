package com.animora.season.service;

import com.animora.season.dto.SeasonDetailResponse;
import com.animora.season.dto.SeasonRequest;
import com.animora.season.dto.SeasonResponse;


import java.util.List;

public interface SeasonService {

    SeasonResponse createSeason(Long animeId, SeasonRequest request);

    List<SeasonResponse> getSeasonsByAnimeId(Long animeId);

    void deleteSeason(Long animeId, Long seasonId);

    SeasonDetailResponse getSeasonDetail(Long seasonId);
}
