package com.animora.favorite.service;

import com.animora.favorite.dto.FavoriteResponse;

import java.util.List;

public interface FavoriteService {

    FavoriteResponse createFavorite(Long animeId);

    void removeFavorite(Long animeId);

    List<FavoriteResponse> getUserFavorites(Long userId);
}
