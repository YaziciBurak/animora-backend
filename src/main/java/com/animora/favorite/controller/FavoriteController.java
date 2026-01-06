package com.animora.favorite.controller;

import com.animora.favorite.dto.FavoriteResponse;
import com.animora.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("users/{userId}/favorites/{animeId}")
    public ResponseEntity<FavoriteResponse> addFavorite(@PathVariable Long userId,
                                                        @PathVariable Long animeId) {
        return ResponseEntity.ok(favoriteService.addFavorite(userId, animeId));
    }

    @DeleteMapping("users/{userId}/favorites/{animeId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId,
                                               @PathVariable Long animeId) {
        favoriteService.removeFavorite(userId, animeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("users/{userId}/favorites")
    public ResponseEntity<List<FavoriteResponse>> getUserFavorites(@PathVariable Long userId) {

        return ResponseEntity.ok(favoriteService.getUserFavorites(userId));
    }
}
