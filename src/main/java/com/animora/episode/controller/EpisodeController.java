package com.animora.episode.controller;

import com.animora.common.pagination.PageResponse;
import com.animora.episode.dto.EpisodeRequest;
import com.animora.episode.dto.EpisodeResponse;
import com.animora.episode.service.EpisodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seasons/{seasonId}/episodes")
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping
    public ResponseEntity<EpisodeResponse> createEpisode(
            @PathVariable Long seasonId,
            @Valid @RequestBody EpisodeRequest request
             ) {
        EpisodeResponse response =
                episodeService.createEpisodeToSeason(seasonId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public PageResponse<EpisodeResponse> getEpisodes(
            @PathVariable Long seasonId,
            @PageableDefault(
                    size = 10,
                    sort = "episodeNumber",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        return episodeService.getEpisodesBySeasonId(seasonId, pageable);
    }
}
