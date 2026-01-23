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

    @NotNull(message = "Episode number is required")
    @Min(value = 1, message = "Episode number must be at least 1")
    private Integer episodeNumber;

    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;

    private LocalDate airDate;

    @NotBlank(message = "Video URL is required")
    private String videoUrl;
}
