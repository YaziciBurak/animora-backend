package com.animora.anime.service.impl;

import com.animora.anime.dto.AnimeRequest;
import com.animora.anime.dto.AnimeResponse;
import com.animora.anime.entity.Anime;
import com.animora.anime.mapper.AnimeMapper;
import com.animora.anime.repository.AnimeRepository;
import com.animora.anime.service.AnimeService;
import com.animora.season.entity.Season;
import com.animora.season.repository.SeasonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnimeServiceImpl implements AnimeService {

    private final AnimeRepository animeRepository;
    private final AnimeMapper animeMapper;
    private final SeasonRepository seasonRepository;

    @Override
    public AnimeResponse createAnime(AnimeRequest request) {
        Anime anime = animeMapper.toEntity(request);
        Anime savedAnime = animeRepository.save(anime);

        if (!seasonRepository.existsByAnimeAndSeasonNumber(savedAnime, 1)) {
            Season season1 = Season.builder()
                    .seasonNumber(1)
                    .anime(savedAnime)
                    .build();

            seasonRepository.save(season1);
        }

        return animeMapper.toResponse(savedAnime);
    }

    @Override
    public AnimeResponse getAnimeById(Long id) {
        Anime anime = animeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found: " +id));
        return animeMapper.toResponse(anime);
    }

    @Override
    public List<AnimeResponse> getAllAnime() {
        return animeRepository.findAll()
                .stream()
                .map(animeMapper::toResponse)
                .toList();
    }

    @Override
    public AnimeResponse updateAnime(Long id, AnimeRequest request) {
        Anime anime = animeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found: " + id));

        anime.setTitle(request.getTitle());
        anime.setDescription(request.getDescription());
        anime.setStatus(request.getStatus());
        anime.setReleaseDate(request.getReleaseDate());
        anime.setCoverImage(request.getCoverImage());

        animeRepository.save(anime);

        return animeMapper.toResponse(anime);
    }

    @Override
    public void deleteAnime(Long id) {
        Anime anime = animeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Anime not found: " + id));
        animeRepository.delete(anime);
    }
}
