package com.animora.favorite.service;

import com.animora.favorite.entity.Favorite;

import java.util.List;

public interface FavoriteService {

    Favorite addToFavorites(Long userId, Long animeId);

    void removeFromFavorites(Long userId, Long animeId);

    List<Favorite> getUserFavorites(Long userId);
}
