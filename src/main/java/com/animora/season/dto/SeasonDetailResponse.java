package com.animora.season.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class SeasonDetailResponse {

    private Long id;
    private int seasonNumber;
    private int episodeCount;
}
