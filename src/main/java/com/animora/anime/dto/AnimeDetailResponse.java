package com.animora.anime.dto;

import com.animora.season.dto.SeasonResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class AnimeDetailResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private String status;
    private String coverImage;

    private List<GenreSummaryResponse> genres;
    private List<SeasonResponse> seasons;
}
