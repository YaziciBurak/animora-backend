package com.animora.season.entity;

import com.animora.anime.entity.Anime;
import com.animora.episode.entity.Episode;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "season")
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int seasonNumber;
    private int releaseYear;

    @ManyToOne
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Episode> episodes = new ArrayList<>();
}
