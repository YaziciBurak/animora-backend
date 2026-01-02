package com.animora.episode.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpisodeResponse {

    private Long id;
    private Integer episodeNumber;
    private String title;
    private Integer duration;
    private LocalDate airDate;
    private String videoUrl;
}
