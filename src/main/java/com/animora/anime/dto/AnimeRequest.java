package com.animora.anime.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimeRequest {

    private String title;
    private String description;
    private LocalDate releaseDate;
    private String status;
    private String coverImage;
}
