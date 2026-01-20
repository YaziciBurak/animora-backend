package com.animora.season.mapper;

import com.animora.anime.entity.Anime;
import com.animora.episode.mapper.EpisodeMapper;
import com.animora.season.dto.SeasonDetailResponse;
import com.animora.season.dto.SeasonRequest;
import com.animora.season.dto.SeasonResponse;
import com.animora.season.entity.Season;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SeasonMapper {

    private final EpisodeMapper episodeMapper;

    public SeasonDetailResponse toDetailResponse(Season season) {
        return SeasonDetailResponse.builder()
                .id(season.getId())
                .seasonNumber(season.getSeasonNumber())
                .episodes(
                        season.getEpisodes() == null
                        ? List.of()
                                : season.getEpisodes()
                                .stream()
                                .map(episodeMapper::toResponse)
                                .toList()
                )
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
}
