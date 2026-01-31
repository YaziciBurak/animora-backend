package com.animora.season.service;

import com.animora.common.pagination.PageResponse;
import com.animora.season.dto.SeasonDetailResponse;
import com.animora.season.dto.SeasonRequest;
import com.animora.season.dto.SeasonResponse;
import org.springframework.data.domain.Pageable;



public interface SeasonService {

    SeasonResponse createSeason(Long animeId, SeasonRequest request);

    PageResponse<SeasonResponse> getSeasonsByAnimeId(Long animeId, Pageable pageable);

    void deleteSeason(Long animeId, Long seasonId);

    SeasonResponse updateSeason(Long animeId, Long seasonId, SeasonRequest request);

    SeasonDetailResponse getSeasonDetail(Long seasonId);
}
