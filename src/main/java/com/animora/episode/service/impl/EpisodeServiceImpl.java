package com.animora.episode.service.impl;

import com.animora.common.pagination.PageResponse;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

        if (episodeRepository.existsBySeasonIdAndEpisodeNumber(seasonId, request.getEpisodeNumber())) {
            throw new IllegalStateException("Episode is already exists in this season:" + request.getEpisodeNumber());
        }

        Episode episode = episodeMapper.toEntity(request);
        episode.setSeason(season);

        episodeRepository.save(episode);

        return episodeMapper.toResponse(episode);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<EpisodeResponse> getEpisodesBySeasonId(Long seasonId, Pageable pageable) {

        if (!seasonRepository.existsById(seasonId)) {
            throw new EntityNotFoundException("Season not found:" + seasonId);
        }

        return PageResponse.from(
                episodeRepository.findBySeasonId(seasonId, pageable)
                        .map(episodeMapper::toResponse)
        );
    }
}
