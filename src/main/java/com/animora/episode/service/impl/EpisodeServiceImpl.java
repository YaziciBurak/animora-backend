package com.animora.episode.service.impl;

import com.animora.common.pagination.PageResponse;
import com.animora.episode.dto.EpisodeRequest;
import com.animora.episode.dto.EpisodeResponse;
import com.animora.episode.entity.Episode;
import com.animora.episode.exception.EpisodeAlreadyExistsException;
import com.animora.episode.exception.EpisodeNotFoundException;
import com.animora.episode.exception.EpisodeNotInSeasonException;
import com.animora.episode.mapper.EpisodeMapper;
import com.animora.episode.repository.EpisodeRepository;
import com.animora.episode.service.EpisodeService;
import com.animora.season.entity.Season;
import com.animora.season.exception.SeasonNotFoundException;
import com.animora.season.repository.SeasonRepository;
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
                .orElseThrow(() -> new SeasonNotFoundException(seasonId));

        if (episodeRepository.existsBySeasonIdAndEpisodeNumber(seasonId, request.getEpisodeNumber())) {
            throw new EpisodeAlreadyExistsException(request.getEpisodeNumber());
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
            throw new SeasonNotFoundException(seasonId);
        }

        return PageResponse.from(
                episodeRepository.findBySeasonId(seasonId, pageable)
                        .map(episodeMapper::toResponse)
        );
    }

    @Override
    @Transactional
    public EpisodeResponse updateEpisode(Long seasonId, Long episodeId, EpisodeRequest request) {

        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new EpisodeNotFoundException(episodeId));

        if (!episode.getSeason().getId().equals(seasonId)) {
            throw new EpisodeNotInSeasonException(episodeId,seasonId);
        }

        int newEpisodeNumber = request.getEpisodeNumber();

        if (episode.getEpisodeNumber() != newEpisodeNumber &&
                episodeRepository.existsBySeasonIdAndEpisodeNumberAndIdNot(
                        seasonId,
                        newEpisodeNumber,
                        episodeId)) {

            throw new EpisodeAlreadyExistsException(newEpisodeNumber);
        }

        episodeMapper.updateEntityFromRequest(request, episode);

        Episode updated = episodeRepository.save(episode);

        return episodeMapper.toResponse(updated);
    }

    @Override
    public void deleteEpisode(Long seasonId, Long episodeId) {

        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new SeasonNotFoundException(seasonId));

        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new EpisodeNotFoundException(episodeId));

        if (!episode.getSeason().getId().equals(season.getId())) {
            throw new EpisodeNotInSeasonException(episodeId, seasonId);
        }

        episodeRepository.delete(episode);
    }
}
