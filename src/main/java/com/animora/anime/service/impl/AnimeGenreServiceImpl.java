package com.animora.anime.service.impl;

import com.animora.anime.entity.Anime;
import com.animora.anime.entity.AnimeGenre;
import com.animora.anime.exception.AnimeNotFoundException;
import com.animora.anime.repository.AnimeGenreRepository;
import com.animora.anime.repository.AnimeRepository;
import com.animora.anime.service.AnimeGenreService;
import com.animora.genre.entity.Genre;
import com.animora.genre.exception.GenreNotFoundException;
import com.animora.genre.repository.GenreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimeGenreServiceImpl implements AnimeGenreService {

    private final AnimeRepository animeRepository;
    private final GenreRepository genreRepository;
    private final AnimeGenreRepository animeGenreRepository;

    @Override
    public void createGenresToAnime(Long animeId, List<Long> genreIds) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));

        for (Long genreId : genreIds) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new GenreNotFoundException(genreId));

            boolean exists = animeGenreRepository.existsByAnimeIdAndGenreId(animeId, genreId);

            if (!exists) {
                AnimeGenre animeGenre = AnimeGenre.builder()
                        .anime(anime)
                        .genre(genre)
                        .build();

                animeGenreRepository.save(animeGenre);
            }
        }
    }
}
