package com.animora.episode.service.impl;

import com.animora.episode.entity.Episode;
import com.animora.episode.repository.EpisodeRepository;
import com.animora.episode.service.EpisodeService;
import com.animora.season.entity.Season;
import com.animora.season.repository.SeasonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final SeasonRepository seasonRepository;

    public EpisodeServiceImpl(EpisodeRepository episodeRepository,
                              SeasonRepository seasonRepository) {
        this.episodeRepository = episodeRepository;
        this.seasonRepository = seasonRepository;
    }

    @Override
    public Episode createEpisode(Long seasonId, Episode episode) {
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new EntityNotFoundException("Season not found"));

        episode.setSeason(season);

        return episodeRepository.save(episode);
    }

    @Override
    public List<Episode> getEpisodesBySeasonId(Long seasonId) {
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new EntityNotFoundException("Season not found"));

        return episodeRepository.findBySeason(season);
    }
}
