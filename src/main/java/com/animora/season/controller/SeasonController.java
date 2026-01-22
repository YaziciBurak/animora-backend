package com.animora.season.controller;

import com.animora.common.pagination.PageResponse;
import com.animora.season.dto.SeasonRequest;
import com.animora.season.dto.SeasonResponse;
import com.animora.season.service.SeasonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/animes/{animeId}/seasons")
@RequiredArgsConstructor
public class SeasonController {

    private final SeasonService seasonService;

    @PostMapping
    public ResponseEntity<SeasonResponse> createSeason(
            @PathVariable Long animeId,
            @Valid @RequestBody SeasonRequest request
            ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(seasonService.createSeason(animeId, request));
    }

    @GetMapping
    public PageResponse<SeasonResponse> getSeasons(
            @PathVariable Long animeId,
            @PageableDefault(
                    sort = "seasonNumber",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ) {
        return seasonService.getSeasonsByAnimeId(animeId, pageable);
    }

    @DeleteMapping("/{seasonId}")
    public ResponseEntity<Void> deleteSeason (
            @PathVariable Long animeId,
            @PathVariable Long seasonId
    ) {
        seasonService.deleteSeason(animeId, seasonId);
        return ResponseEntity.noContent().build();
    }

}
