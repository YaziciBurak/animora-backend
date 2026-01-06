package com.animora.favorite.service;

import com.animora.favorite.dto.FavoriteResponse;

import java.util.List;

public interface FavoriteService {

    FavoriteResponse addFavorite(Long userId, Long animeId);

    void removeFavorite(Long userId, Long animeId);

    List<FavoriteResponse> getUserFavorites(Long userId);
}
