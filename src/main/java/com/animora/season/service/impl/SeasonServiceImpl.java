package com.animora.season.service.impl;

import com.animora.anime.entity.Anime;
import com.animora.anime.repository.AnimeRepository;
import com.animora.season.entity.Season;
import com.animora.season.repository.SeasonRepository;
import com.animora.season.service.SeasonService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SeasonServiceImpl implements SeasonService {

    private final SeasonRepository seasonRepository;
    private final AnimeRepository animeRepository;

    public SeasonServiceImpl(SeasonRepository seasonRepository,
                             AnimeRepository animeRepository) {
        this.seasonRepository = seasonRepository;
        this.animeRepository = animeRepository;
    }

    @Override
    public Season createSeason(Anime anime, int seasonNumber) {
        Season season = Season.builder().
                anime(anime)
                .seasonNumber(seasonNumber)
                .build();

        return seasonRepository.save(season);
    }

    @Override
    public List<Season> getSeasonsByAnimeId(Long animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new EntityNotFoundException("Anime not found"));

        return seasonRepository.findByAnime(anime);
    }
}
