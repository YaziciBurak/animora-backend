package com.animora.anime.controller;

import com.animora.anime.dto.AnimeGenreRequest;
import com.animora.anime.service.AnimeGenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animes/genres")
@RequiredArgsConstructor
public class AnimeGenreController {

    private final AnimeGenreService animeGenreService;

    @PostMapping("/{animeId}")
    public ResponseEntity<Void> createGenresToAnime(@PathVariable Long animeId,
                                                 @RequestBody @Valid AnimeGenreRequest request) {
        animeGenreService.createGenresToAnime(animeId, request.getGenreIds());
        return ResponseEntity.noContent().build();
    }
}
