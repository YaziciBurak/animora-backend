package com.animora.anime.mapper;

import com.animora.anime.dto.AnimeRequest;
import com.animora.anime.dto.AnimeResponse;
import com.animora.anime.dto.GenreSummaryResponse;
import com.animora.anime.entity.Anime;
import com.animora.anime.entity.AnimeGenre;
import com.animora.genre.entity.Genre;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnimeMapper {

    public Anime toEntity(AnimeRequest request) {
        return Anime.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .releaseDate(request.getReleaseDate())
                .status(request.getStatus())
                .coverImage(request.getCoverImage())
                .build();
    }

    public AnimeResponse toResponse(Anime anime) {
        return AnimeResponse.builder()
                .id(anime.getId())
                .title(anime.getTitle())
                .description(anime.getDescription())
                .releaseDate(anime.getReleaseDate())
                .status(anime.getStatus())
                .coverImage(anime.getCoverImage())
                .genres(
                        anime.getGenres() == null
                                ? List.of()
                                : anime.getGenres()
                                .stream()
                                .map(this::toGenreSummary)
                                .toList()
                )
                .build();
    }

    private GenreSummaryResponse toGenreSummary(AnimeGenre animeGenre) {
        Genre genre = animeGenre.getGenre();

        return GenreSummaryResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

}
