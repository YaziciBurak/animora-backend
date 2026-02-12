package com.animora.favorite.controller;

import com.animora.favorite.dto.FavoriteRequest;
import com.animora.favorite.dto.FavoriteResponse;
import com.animora.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    @PreAuthorize("hasAuthority('FAVORITE_ADD')")
    public ResponseEntity<FavoriteResponse> createFavorite(@RequestBody FavoriteRequest request) {

        FavoriteResponse response = favoriteService.createFavorite(request.getAnimeId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{animeId}")
    @PreAuthorize("hasAuthority('FAVORITE_DELETE_OWN') or hasAuthority('FAVORITE_DELETE_ANY')")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long animeId) {
        favoriteService.removeFavorite(animeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getUserFavorites(@PathVariable Long userId) {

        return ResponseEntity.ok(favoriteService.getUserFavorites(userId));
    }
}
