package com.animora.anime.controller;

import com.animora.anime.dto.AnimeDetailResponse;
import com.animora.anime.dto.AnimeRequest;
import com.animora.anime.dto.AnimeResponse;
import com.animora.anime.service.AnimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animes")
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;

    @PostMapping
    @PreAuthorize("hasAuthority('ANIME_CREATE')")
    public ResponseEntity<AnimeResponse> createAnime(@Valid @RequestBody AnimeRequest request) {
        AnimeResponse response = animeService.createAnime(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimeResponse> getAnime(@PathVariable Long id) {
        AnimeResponse response = animeService.getAnimeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<AnimeDetailResponse> getAnimeDetail(@PathVariable Long id) {
        return ResponseEntity.ok(animeService.getAnimeDetail(id));
    }

    @GetMapping
    public ResponseEntity<List<AnimeResponse>> getAllAnime() {
        List<AnimeResponse> responses = animeService.getAllAnime();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ANIME_UPDATE')")
    public ResponseEntity<AnimeResponse> updateAnime(
            @PathVariable Long id,@Valid @RequestBody AnimeRequest request) {
        AnimeResponse response = animeService.updateAnime(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ANIME_DELETE')")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        animeService.deleteAnime(id);
        return ResponseEntity.noContent().build();
    }
}
