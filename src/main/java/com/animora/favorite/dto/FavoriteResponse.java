package com.animora.favorite.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteResponse {

    private Long id;
    private Long animeId;
    private String animeTitle;
    private LocalDate createdAt;
}
