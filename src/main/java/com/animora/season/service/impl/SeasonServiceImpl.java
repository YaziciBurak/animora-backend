package com.animora.season.service.impl;

import com.animora.anime.entity.Anime;
import com.animora.anime.exception.AnimeNotFoundException;
import com.animora.anime.repository.AnimeRepository;
import com.animora.common.pagination.PageResponse;
import com.animora.episode.repository.EpisodeRepository;
import com.animora.season.dto.SeasonDetailResponse;
import com.animora.season.dto.SeasonRequest;
import com.animora.season.dto.SeasonResponse;
import com.animora.season.entity.Season;
import com.animora.season.exception.SeasonAlreadyExistsException;
import com.animora.season.exception.SeasonDoesNotBelongToAnimeException;
import com.animora.season.exception.SeasonHasEpisodesException;
import com.animora.season.exception.SeasonNotFoundException;
import com.animora.season.mapper.SeasonMapper;
import com.animora.season.repository.SeasonRepository;
import com.animora.season.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class SeasonServiceImpl implements SeasonService {

    private final SeasonRepository seasonRepository;
    private final AnimeRepository animeRepository;
    private final EpisodeRepository episodeRepository;
    private final SeasonMapper seasonMapper;

    @Override
    public SeasonResponse createSeason(Long animeId, SeasonRequest request) {

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));

        if (seasonRepository.existsByAnimeAndSeasonNumber(anime, request.getSeasonNumber())) {
            throw new SeasonAlreadyExistsException(request.getSeasonNumber());
        }

        Season season = seasonMapper.toEntity(request, anime);
        Season saved = seasonRepository.save(season);

        return seasonMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SeasonResponse> getSeasonsByAnimeId(Long animeId, Pageable pageable) {

        if (!animeRepository.existsById(animeId)) {
            throw new AnimeNotFoundException(animeId);
        }

        return PageResponse.from(
                seasonRepository.findByAnime_IdOrderBySeasonNumberAsc(animeId, pageable)
                        .map(seasonMapper::toResponse)
        );
    }

    @Override
    @Transactional
    public SeasonResponse updateSeason(Long animeId, Long seasonId, SeasonRequest request) {

        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new SeasonNotFoundException(seasonId));

        int newSeasonNumber = request.getSeasonNumber();

        if (season.getSeasonNumber() != newSeasonNumber &&
                seasonRepository.existsByAnimeAndSeasonNumberAndIdNot(
                        season.getAnime(),
                        newSeasonNumber,
                        seasonId)) {

            throw new SeasonAlreadyExistsException(newSeasonNumber);
        }

        if (seasonRepository.existsByAnimeAndSeasonNumberAndIdNot(
                season.getAnime(),
                request.getSeasonNumber(),
                seasonId
        )) {
            throw new SeasonAlreadyExistsException(request.getSeasonNumber());
        }

        seasonMapper.updateEntityFromRequest(request, season);

        Season updated = seasonRepository.save(season);

        return seasonMapper.toResponse(updated);
    }

    @Override
    public void deleteSeason(Long animeId, Long seasonId) {

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));

        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new SeasonNotFoundException(seasonId));

        if (!season.getAnime().getId().equals(anime.getId())) {
            throw new SeasonDoesNotBelongToAnimeException(animeId, seasonId);
        }

        if (episodeRepository.existsBySeasonId(seasonId)) {
            throw new SeasonHasEpisodesException(seasonId);
        }

        seasonRepository.delete(season);
    }

    @Override
    @Transactional(readOnly = true)
    public SeasonDetailResponse getSeasonDetail(Long seasonId) {
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new SeasonNotFoundException(seasonId));

        return seasonMapper.toDetailResponse(season);
    }
}
