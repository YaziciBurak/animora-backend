package com.animora.season.dto;

import com.animora.episode.dto.EpisodeResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SeasonDetailResponse {

    private Long id;
    private int seasonNumber;
    private List<EpisodeResponse> episodes;
}
