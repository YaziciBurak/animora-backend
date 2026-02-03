package com.animora.genre.controller;


import com.animora.genre.dto.GenreRequest;
import com.animora.genre.dto.GenreResponse;
import com.animora.genre.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    @PreAuthorize("hasAuthority('ANIME_CREATE')")
    public ResponseEntity<GenreResponse> createGenre(@Valid @RequestBody GenreRequest request) {
        return ResponseEntity.ok(genreService.createGenre(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ANIME_READ')")
    public ResponseEntity<GenreResponse> getGenre(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ANIME_READ')")
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ANIME_UPDATE')")
    public ResponseEntity<GenreResponse> updateGenre(
            @PathVariable Long id,
            @Valid @RequestBody GenreRequest request
    ) {
        return ResponseEntity.ok(genreService.updateGenre(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ANIME_DELETE')")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
