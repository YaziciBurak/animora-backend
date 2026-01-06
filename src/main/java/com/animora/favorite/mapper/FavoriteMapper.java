package com.animora.favorite.mapper;

import com.animora.favorite.dto.FavoriteResponse;
import com.animora.favorite.entity.Favorite;
import org.springframework.stereotype.Component;

@Component
public class FavoriteMapper {

    public FavoriteResponse toResponse(Favorite favorite) {
        return FavoriteResponse.builder()
                .id(favorite.getId())
                .animeId(favorite.getAnime().getId())
                .animeTitle(favorite.getAnime().getTitle())
                .createdAt(favorite.getCreatedAt())
                .build();
    }
}
