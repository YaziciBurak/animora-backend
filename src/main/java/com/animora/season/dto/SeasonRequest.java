package com.animora.season.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeasonRequest {

    @NotNull
    @Min(value = 1, message = "Season number must be at least 1")
    private int seasonNumber;
}
