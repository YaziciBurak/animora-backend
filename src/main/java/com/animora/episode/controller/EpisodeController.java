package com.animora.episode.controller;

import com.animora.episode.dto.EpisodeRequest;
import com.animora.episode.dto.EpisodeResponse;
import com.animora.episode.service.EpisodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<EpisodeResponse>> getEpisodes(
            @PathVariable Long seasonId
    ) {
        return ResponseEntity.ok(
                episodeService.getEpisodesBySeasonId(seasonId)
        );
    }
}
