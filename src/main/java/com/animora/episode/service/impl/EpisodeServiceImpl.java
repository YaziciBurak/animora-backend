package com.animora.episode.service.impl;

import com.animora.episode.dto.EpisodeRequest;
import com.animora.episode.dto.EpisodeResponse;
import com.animora.episode.entity.Episode;
import com.animora.episode.mapper.EpisodeMapper;
import com.animora.episode.repository.EpisodeRepository;
import com.animora.episode.service.EpisodeService;
import com.animora.season.entity.Season;
import com.animora.season.repository.SeasonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final SeasonRepository seasonRepository;
    private final EpisodeMapper episodeMapper;

    @Override
    public EpisodeResponse createEpisodeToSeason(Long seasonId, EpisodeRequest request) {

        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new EntityNotFoundException("Season not found" + seasonId));

        boolean exists = episodeRepository
                .existsBySeasonIdAndEpisodeNumber(seasonId, request.getEpisodeNumber());

        if (exists) {
            throw new IllegalStateException(
                    "Episode already exists in this season:" + request.getEpisodeNumber());
        }

        Episode episode = episodeMapper.toEntity(request);
        episode.setSeason(season);

        episodeRepository.save(episode);

        return episodeMapper.toResponse(episode);
    }

    @Override
    @Transactional
    public List<EpisodeResponse> getEpisodesBySeasonId(Long seasonId) {
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new EntityNotFoundException("Season not found: " + seasonId));

        return episodeRepository.findBySeason(season)
                .stream()
                .map(episodeMapper::toResponse)
                .toList();
    }
}
