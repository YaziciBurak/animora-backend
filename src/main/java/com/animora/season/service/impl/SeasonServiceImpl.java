package com.animora.season.service.impl;

import com.animora.anime.entity.Anime;
import com.animora.anime.repository.AnimeRepository;
import com.animora.common.pagination.PageResponse;
import com.animora.season.dto.SeasonDetailResponse;
import com.animora.season.dto.SeasonRequest;
import com.animora.season.dto.SeasonResponse;
import com.animora.season.entity.Season;
import com.animora.season.exception.SeasonAlreadyExistsException;
import com.animora.season.exception.SeasonHasEpisodesException;
import com.animora.season.mapper.SeasonMapper;
import com.animora.season.repository.SeasonRepository;
import com.animora.season.service.SeasonService;
import jakarta.persistence.EntityNotFoundException;
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
    private final SeasonMapper seasonMapper;

    @Override
    public SeasonResponse createSeason(Long animeId, SeasonRequest request) {

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new EntityNotFoundException("Anime not found"));

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
            throw new EntityNotFoundException("Anime not found");
        }

        return PageResponse.from(
                seasonRepository.findByAnime_IdOrderBySeasonNumberAsc(animeId, pageable)
                        .map(seasonMapper::toResponse)
        );
    }

    @Override
    public void deleteSeason(Long animeId, Long seasonId) {

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new EntityNotFoundException("Anime not found"));

        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new EntityNotFoundException("Season not found"));

        if (!season.getAnime().getId().equals(anime.getId())) {
            throw new IllegalStateException("Season does not belong to this anime");
        }

        if (!season.getEpisodes().isEmpty()) {
            throw new SeasonHasEpisodesException(seasonId);
        }

        seasonRepository.delete(season);
    }

    @Override
    @Transactional(readOnly = true)
    public SeasonDetailResponse getSeasonDetail(Long seasonId) {
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new EntityNotFoundException("Season not found"));

        return seasonMapper.toDetailResponse(season);
    }
}
