package com.animora.episode.controller;

import com.animora.episode.dto.EpisodeRequest;
import com.animora.episode.dto.EpisodeResponse;
import com.animora.episode.service.EpisodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seasons")
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping("/{seasonId}/episodes")
    public ResponseEntity<EpisodeResponse> addEpisode(
            @PathVariable Long seasonId,
            @Valid @RequestBody EpisodeRequest request
             ) {
        EpisodeResponse response =
                episodeService.addEpisodeToSeason(seasonId, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{seasonId}/episodes")
    public ResponseEntity<List<EpisodeResponse>> getEpisodes(
            @PathVariable Long seasonId
    ) {
        return ResponseEntity.ok(
                episodeService.getEpisodesBySeasonId(seasonId)
        );
    }
}
