package com.animora.season.mapper;

import com.animora.anime.entity.Anime;
import com.animora.season.dto.SeasonDetailResponse;
import com.animora.season.dto.SeasonRequest;
import com.animora.season.dto.SeasonResponse;
import com.animora.season.entity.Season;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SeasonMapper {


    public SeasonDetailResponse toDetailResponse(Season season) {
        return SeasonDetailResponse.builder()
                .id(season.getId())
                .seasonNumber(season.getSeasonNumber())
                .episodeCount(season.getEpisodes().size())
                .build();
    }

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
                .episodeCount(
                        season.getEpisodes() == null
                        ? 0
                                : season.getEpisodes().size()
                )
                .build();
    }

    public void updateEntityFromRequest(SeasonRequest request, Season season) {
        season.setSeasonNumber(request.getSeasonNumber());
        season.setReleaseYear(request.getReleaseYear());
    }
}
