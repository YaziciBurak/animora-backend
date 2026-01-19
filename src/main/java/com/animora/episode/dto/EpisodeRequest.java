package com.animora.episode.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpisodeRequest {

    @NotNull
    @Min(1)
    private Integer episodeNumber;

    @NotNull
    private String title;

    private Integer duration;

    private LocalDate airDate;

    @NotBlank
    private String videoUrl;
}
