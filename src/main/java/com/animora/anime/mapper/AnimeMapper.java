package com.animora.anime.mapper;

import com.animora.anime.dto.AnimeDetailResponse;
import com.animora.anime.dto.AnimeRequest;
import com.animora.anime.dto.AnimeResponse;
import com.animora.anime.dto.GenreSummaryResponse;
import com.animora.anime.entity.Anime;
import com.animora.anime.entity.AnimeGenre;
import com.animora.genre.entity.Genre;
import com.animora.season.mapper.SeasonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnimeMapper {

    private final SeasonMapper seasonMapper;

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

    public void updateEntityFromRequest(AnimeRequest request, Anime anime) {
        anime.setTitle(request.getTitle().trim());
        anime.setDescription(request.getDescription());
        anime.setReleaseDate(request.getReleaseDate());
        anime.setStatus(request.getStatus());
        anime.setCoverImage(request.getCoverImage());
    }

    public AnimeDetailResponse toDetailResponse(Anime anime) {
        return AnimeDetailResponse.builder()
                .id(anime.getId())
                .title(anime.getTitle())
                .description(anime.getDescription())
                .releaseDate(anime.getReleaseDate())
                .status(anime.getStatus())
                .coverImage(anime.getCoverImage())
                .genres(anime.getGenres().stream()
                        .map(ag -> new GenreSummaryResponse(
                                ag.getGenre().getId(),
                                ag.getGenre().getName()
                        ))
                        .toList())
                .seasons(anime.getSeasons().stream()
                        .map(seasonMapper::toResponse)
                        .toList())
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
