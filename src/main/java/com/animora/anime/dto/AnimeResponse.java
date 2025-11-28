package com.animora.anime.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimeResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private String status;
    private String coverImage;
}
