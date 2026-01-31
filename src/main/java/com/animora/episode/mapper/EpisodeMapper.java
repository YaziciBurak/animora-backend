package com.animora.episode.mapper;

import com.animora.episode.dto.EpisodeRequest;
import com.animora.episode.dto.EpisodeResponse;
import com.animora.episode.entity.Episode;
import org.springframework.stereotype.Component;

@Component
public class EpisodeMapper {

    public Episode toEntity(EpisodeRequest request) {
        return Episode.builder()
                .episodeNumber(request.getEpisodeNumber())
                .title(request.getTitle())
                .videoUrl(request.getVideoUrl())
                .airDate(request.getAirDate())
                .duration(request.getDuration())
                .build();
    }

    public EpisodeResponse toResponse(Episode episode) {
        return EpisodeResponse.builder()
                .id(episode.getId())
                .episodeNumber(episode.getEpisodeNumber())
                .title(episode.getTitle())
                .duration(episode.getDuration())
                .airDate(episode.getAirDate())
                .videoUrl(episode.getVideoUrl())
                .build();
    }

    public void updateEntityFromRequest(EpisodeRequest request, Episode episode) {
        episode.setEpisodeNumber(request.getEpisodeNumber());
        episode.setTitle(request.getTitle());
        episode.setDuration(request.getDuration());
        episode.setAirDate(request.getAirDate());
        episode.setVideoUrl(request.getVideoUrl());
    }
}
