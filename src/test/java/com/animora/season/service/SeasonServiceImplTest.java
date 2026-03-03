package com.animora.season.service;

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
import com.animora.season.service.impl.SeasonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeasonServiceImplTest {

    @Mock
    private SeasonRepository seasonRepository;

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private SeasonMapper seasonMapper;

    @InjectMocks
    private SeasonServiceImpl seasonService;

    private Anime anime;
    private Season season;
    private SeasonRequest request;
    private SeasonResponse response;

    @BeforeEach
    void setUp() {
        anime = Anime.builder()
                .id(1L)
                .title("Bleach")
                .build();

        season = Season.builder()
                .id(1L)
                .seasonNumber(1)
                .anime(anime)
                .episodes(new ArrayList<>())
                .build();

        request = SeasonRequest.builder()
                .seasonNumber(1)
                .releaseYear(2000)
                .build();

        response = SeasonResponse.builder()
                .id(1L)
                .seasonNumber(1)
                .episodeCount(0)
                .build();
    }

    @Test
    void createSeason_success() {
        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(seasonRepository.existsByAnimeAndSeasonNumber(anime, 1)).thenReturn(false);
        when(seasonMapper.toEntity(request, anime)).thenReturn(season);
        when(seasonRepository.save(season)).thenReturn(season);
        when(seasonMapper.toResponse(season)).thenReturn(response);

        SeasonResponse result = seasonService.createSeason(1L, request);

        assertEquals(1, result.getSeasonNumber());
        verify(seasonRepository).save(season);
    }

    @Test
    void createSeason_whenAnimeNotFound_throwException() {
        when(animeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AnimeNotFoundException.class,
                () -> seasonService.createSeason(1L, request));
    }

    @Test
    void createSeason_whenSeasonAlreadyExists_throwException() {
        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(seasonRepository.existsByAnimeAndSeasonNumber(anime, 1)).thenReturn(true);

        assertThrows(SeasonAlreadyExistsException.class,
                () -> seasonService.createSeason(1L, request));
    }

    @Test
    void getSeasonsByAnimeId_success() {
        Page<Season> page = new PageImpl<>(List.of(season));

        when(animeRepository.existsById(1L)).thenReturn(true);
        when(seasonRepository.findByAnime_IdOrderBySeasonNumberAsc(eq(1L), any(Pageable.class)))
                .thenReturn(page);
        when(seasonMapper.toResponse(season)).thenReturn(response);

        PageResponse<SeasonResponse> result =
                seasonService.getSeasonsByAnimeId(1L, Pageable.unpaged());

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getContent().get(0).getSeasonNumber());
    }

    @Test
    void getSeasonByAnimeId_whenAnimeNotFound_throwException() {
        when(animeRepository.existsById(1L)).thenReturn(false);

        assertThrows(AnimeNotFoundException.class,
                () -> seasonService.getSeasonsByAnimeId(1L, Pageable.unpaged()));
    }

    @Test
    void updateSeason_success() {
        SeasonRequest updateRequest = SeasonRequest.builder()
                .seasonNumber(2)
                .releaseYear(2021)
                .build();

        Season updatedSeason = Season.builder()
                .id(1L)
                .seasonNumber(2)
                .anime(anime)
                .build();

        SeasonResponse updatedResponse = SeasonResponse.builder()
                .id(1L)
                .seasonNumber(2)
                .episodeCount(0)
                .build();

        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(seasonRepository.existsByAnimeAndSeasonNumberAndIdNot(anime, 2, 1L)).thenReturn(false);
        when(seasonRepository.save(season)).thenReturn(updatedSeason);
        when(seasonMapper.toResponse(updatedSeason)).thenReturn(updatedResponse);

        doAnswer(invocation -> {
            season.setSeasonNumber(2);
            return null;
        }).when(seasonMapper).updateEntityFromRequest(updateRequest, season);

        SeasonResponse result = seasonService.updateSeason(1L, 1L, updateRequest);

        assertEquals(2, result.getSeasonNumber());
        verify(seasonMapper).updateEntityFromRequest(updateRequest, season);
    }

    @Test
    void updateSeason_whenSeasonNotFound_throwException() {
        when(seasonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SeasonNotFoundException.class,
                () -> seasonService.updateSeason(1L, 1L, request));
    }

    @Test
    void updateSeason_whenDuplicatedSeasonNumber_throwException() {
        SeasonRequest updateRequest = SeasonRequest.builder()
                .seasonNumber(2)
                .releaseYear(2021)
                .build();

        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(seasonRepository.existsByAnimeAndSeasonNumberAndIdNot(anime, 2, 1L)).thenReturn(true);

        assertThrows(SeasonAlreadyExistsException.class,
                () -> seasonService.updateSeason(1L, 1L, updateRequest));
    }

    @Test
    void deleteSeason_success() {
        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(episodeRepository.existsBySeasonId(1L)).thenReturn(false);

        seasonService.deleteSeason(1L, 1L);

        verify(seasonRepository).delete(season);
    }

    @Test
    void deleteSeason_whenAnimeNotFound_throwException() {
        when(animeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AnimeNotFoundException.class,
                () -> seasonService.deleteSeason(1L, 1L));
    }

    @Test
    void deleteSeason_whenSeasonNotFound_throwException() {
        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(seasonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SeasonNotFoundException.class,
                () -> seasonService.deleteSeason(1L, 1L));
    }

    @Test
    void deleteSeason_whenSeasonDoesNotBelongToAnime_throwException() {
        Anime otherAnime = Anime.builder().id(2L).build();
        season.setAnime(otherAnime);

        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));

        assertThrows(SeasonDoesNotBelongToAnimeException.class,
                () -> seasonService.deleteSeason(1L, 1L));
    }

    @Test
    void deleteSeason_whenHasEpisodes_throwException() {
        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(episodeRepository.existsBySeasonId(1L)).thenReturn(true);

        assertThrows(SeasonHasEpisodesException.class,
                () -> seasonService.deleteSeason(1L, 1L));
    }

    @Test
    void getSeasonDetail_success() {
        SeasonDetailResponse detailResponse = SeasonDetailResponse.builder()
                .id(1L)
                .seasonNumber(1)
                .episodeCount(0)
                .build();

        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(seasonMapper.toDetailResponse(season)).thenReturn(detailResponse);

        SeasonDetailResponse result = seasonService.getSeasonDetail(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getSeasonDetail_whenNotFound_throwException() {
        when(seasonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SeasonNotFoundException.class,
                () -> seasonService.getSeasonDetail(1L));
    }
}
