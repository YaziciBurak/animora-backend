package com.animora.anime.mapper;

import com.animora.anime.dto.AnimeRequest;
import com.animora.anime.dto.AnimeResponse;
import com.animora.anime.entity.Anime;
import org.springframework.stereotype.Component;

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
                .build();
    }


}
