package com.animora.season.service.impl;

import com.animora.anime.entity.Anime;
import com.animora.anime.repository.AnimeRepository;
import com.animora.season.dto.SeasonRequest;
import com.animora.season.dto.SeasonResponse;
import com.animora.season.entity.Season;
import com.animora.season.mapper.SeasonMapper;
import com.animora.season.repository.SeasonRepository;
import com.animora.season.service.SeasonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeasonServiceImpl implements SeasonService {

    private final SeasonRepository seasonRepository;
    private final AnimeRepository animeRepository;
    private final SeasonMapper seasonMapper;

    @Override
    public SeasonResponse createSeason(Long animeId, SeasonRequest request) {

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new EntityNotFoundException("Anime not found"));

        if (seasonRepository.existsByAnimeAndSeasonNumber(anime, request.getSeasonNumber())) {
            throw new IllegalStateException("Season already exists");
        }

        Season season = seasonMapper.toEntity(request, anime);
        Season saved = seasonRepository.save(season);

        return seasonMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeasonResponse> getSeasonsByAnimeId(Long animeId) {

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new EntityNotFoundException("Anime not found"));

        return seasonRepository.findByAnimeOrderBySeasonNumberAsc(anime)
                .stream()
                .map(seasonMapper::toResponse)
                .toList();
    }

    public void deleteSeason(Long animeId, Long seasonId) {

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new EntityNotFoundException("Anime not found"));

        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new EntityNotFoundException("Season not found"));

        if (!season.getAnime().getId().equals(anime.getId())) {
            throw new IllegalStateException("Season does not belong to this anime");
        }

        seasonRepository.delete(season);
    }
}
