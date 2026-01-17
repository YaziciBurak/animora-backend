package com.animora.season.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeasonResponse {

    private Long id;
    private int seasonNumber;
}
