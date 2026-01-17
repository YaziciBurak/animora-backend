package com.animora.favorite.controller;

import com.animora.favorite.dto.FavoriteRequest;
import com.animora.favorite.dto.FavoriteResponse;
import com.animora.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<FavoriteResponse> createFavorite(@PathVariable Long userId,
                                                        @RequestBody FavoriteRequest request) {

        FavoriteResponse response = favoriteService.createFavorite(userId, request.getAnimeId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{animeId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId,
                                               @PathVariable Long animeId) {
        favoriteService.removeFavorite(userId, animeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getUserFavorites(@PathVariable Long userId) {

        return ResponseEntity.ok(favoriteService.getUserFavorites(userId));
    }
}
