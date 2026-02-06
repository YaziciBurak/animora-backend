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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seasons/{seasonId}/episodes")
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping
    @PreAuthorize("hasAuthority('ANIME_CREATE')")
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

    @PutMapping("/{episodeId}")
    @PreAuthorize("hasAuthority('ANIME_UPDATE')")
    public ResponseEntity<EpisodeResponse> updateEpisode(
            @PathVariable Long seasonId,
            @PathVariable Long episodeId,
            @Valid @RequestBody EpisodeRequest request
    ) {
        return ResponseEntity.ok(
                episodeService.updateEpisode(seasonId, episodeId, request)
        );
    }

    @DeleteMapping("/{episodeId}")
    @PreAuthorize("hasAuthority('ANIME_DELETE')")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long seasonId,
                                              @PathVariable Long episodeId) {
        episodeService.deleteEpisode(seasonId, episodeId);
        return ResponseEntity.noContent().build();
    }
}
