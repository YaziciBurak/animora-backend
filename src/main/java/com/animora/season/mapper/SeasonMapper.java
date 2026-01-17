package com.animora.season.mapper;

import com.animora.anime.entity.Anime;
import com.animora.season.dto.SeasonRequest;
import com.animora.season.dto.SeasonResponse;
import com.animora.season.entity.Season;
import org.springframework.stereotype.Component;

@Component
public class SeasonMapper {

    public Season toEntity(SeasonRequest request, Anime anime) {
        return Season.builder()
                .seasonNumber(request.getSeasonNumber())
                .anime(anime)
                .build();
    }

    public SeasonResponse toResponse(Season season) {
        return SeasonResponse.builder()
                .id(season.getId())
                .seasonNumber(season.getSeasonNumber())
                .build();
    }
}
