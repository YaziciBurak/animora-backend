package com.animora.episode.entity;


import com.animora.season.entity.Season;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "episode",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"season_id", "episode_number"})
        }
)
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int episodeNumber;

    private String title;

    private int duration;

    private LocalDate airDate;

    private String videoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;
}
