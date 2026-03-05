package com.animora.episode.service;

import com.animora.common.pagination.PageResponse;
import com.animora.episode.dto.EpisodeRequest;
import com.animora.episode.dto.EpisodeResponse;
import com.animora.episode.entity.Episode;
import com.animora.episode.exception.EpisodeAlreadyExistsException;
import com.animora.episode.exception.EpisodeNotFoundException;
import com.animora.episode.exception.EpisodeNotInSeasonException;
import com.animora.episode.mapper.EpisodeMapper;
import com.animora.episode.repository.EpisodeRepository;
import com.animora.episode.service.impl.EpisodeServiceImpl;
import com.animora.season.entity.Season;
import com.animora.season.exception.SeasonNotFoundException;
import com.animora.season.repository.SeasonRepository;
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
class EpisodeServiceImplTest {

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private SeasonRepository seasonRepository;

    @Mock
    private EpisodeMapper episodeMapper;

    @InjectMocks
    private EpisodeServiceImpl episodeService;

    private Season season;
    private Episode episode;
    private EpisodeRequest request;
    private EpisodeResponse response;

    @BeforeEach
    void setUp() {

        season = Season.builder()
                .id(1L)
                .seasonNumber(1)
                .episodes(new ArrayList<>())
                .build();

        episode = Episode.builder()
                .id(1L)
                .episodeNumber(1)
                .season(season)
                .build();

        request = EpisodeRequest.builder()
                .episodeNumber(1)
                .title("Episode 1")
                .build();

        response = EpisodeResponse.builder()
                .id(1L)
                .episodeNumber(1)
                .title("Episode 1")
                .build();
    }

    @Test
    void createEpisode_success() {
        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(episodeRepository.existsBySeasonIdAndEpisodeNumber(1L,1)).thenReturn(false);
        when(episodeMapper.toEntity(request)).thenReturn(episode);
        when(episodeMapper.toResponse(episode)).thenReturn(response);

        EpisodeResponse result =
                episodeService.createEpisodeToSeason(1L, request);

        assertEquals(1, result.getEpisodeNumber());

        verify(episodeRepository).save(episode);
    }

    @Test
    void createEpisode_whenSeasonNotFound_throwException() {
        when(seasonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SeasonNotFoundException.class,
                () -> episodeService.createEpisodeToSeason(1L, request));
    }

    @Test
    void createEpisode_whenEpisodeAlreadyExists_throwException() {
        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(episodeRepository.existsBySeasonIdAndEpisodeNumber(1L, 1)).thenReturn(true);

        assertThrows(EpisodeAlreadyExistsException.class,
                () -> episodeService.createEpisodeToSeason(1L, request));
    }

    @Test
    void getEpisodesBySeasonId_success() {
        Page<Episode> page = new PageImpl<>(List.of(episode));

        when(seasonRepository.existsById(1L)).thenReturn(true);
        when(episodeRepository.findBySeasonId(eq(1L), any(Pageable.class)))
                .thenReturn(page);

        when(episodeMapper.toResponse(episode)).thenReturn(response);

        PageResponse<EpisodeResponse> result =
                episodeService.getEpisodesBySeasonId(1L, Pageable.unpaged());

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getContent().get(0).getEpisodeNumber());
    }

    @Test
    void getEpisodeBySeasonId_whenSeasonNotFound_throwException() {
        when(seasonRepository.existsById(1L)).thenReturn(false);

        assertThrows(SeasonNotFoundException.class,
                () -> episodeService.getEpisodesBySeasonId(1L, Pageable.unpaged()));
    }

    @Test
    void updateEpisode_success() {
        EpisodeRequest updateRequest = EpisodeRequest.builder()
                .episodeNumber(2)
                .title("Episode 2")
                .build();

        Episode updatedEpisode = Episode.builder()
                .id(1L)
                .episodeNumber(2)
                .season(season)
                .build();

        EpisodeResponse updatedResponse = EpisodeResponse.builder()
                .id(1L)
                .episodeNumber(2)
                .title("Episode 2")
                .build();

        when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));
        when(episodeRepository.existsBySeasonIdAndEpisodeNumberAndIdNot(1L,2,1L))
                .thenReturn(false);

        when(episodeRepository.save(episode)).thenReturn(updatedEpisode);
        when(episodeMapper.toResponse(updatedEpisode)).thenReturn(updatedResponse);

        doAnswer(invocation -> {
            episode.setEpisodeNumber(2);
            return null;
        }).when(episodeMapper).updateEntityFromRequest(updateRequest, episode);

        EpisodeResponse result =
                episodeService.updateEpisode(1L,1L,updateRequest);

        assertEquals(2, result.getEpisodeNumber());

        verify(episodeMapper).updateEntityFromRequest(updateRequest, episode);
    }

    @Test
    void updateEpisode_whenEpisodeNotInSeason_throwException() {
        Season otherSeason = Season.builder().id(2L).build();
        episode.setSeason(otherSeason);

        when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));

        assertThrows(EpisodeNotInSeasonException.class,
                () -> episodeService.updateEpisode(1L,1L,request));
    }

    @Test
    void updateEpisode_whenEpisodeNumberDuplicate_throwException() {
        EpisodeRequest updateRequest = EpisodeRequest.builder()
                .episodeNumber(2)
                .title("Episode 2")
                .build();

        when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));
        when(episodeRepository.existsBySeasonIdAndEpisodeNumberAndIdNot(1L,2,1L))
                .thenReturn(true);

        assertThrows(EpisodeAlreadyExistsException.class,
                () -> episodeService.updateEpisode(1L,1L, updateRequest));
    }

    @Test
    void deleteEpisode_success() {
        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));

        episodeService.deleteEpisode(1L,1L);

        verify(episodeRepository).delete(episode);
    }

    @Test
    void deleteEpisode_whenSeasonNotFound_throwException() {
        when(seasonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SeasonNotFoundException.class,
                () -> episodeService.deleteEpisode(1L,1L));
    }

    @Test
    void deleteEpisode_whenEpisodeNotFound_throwException() {
        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(episodeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EpisodeNotFoundException.class,
                () -> episodeService.deleteEpisode(1L,1L));
    }

    @Test
    void deleteEpisode_whenEpisodeNotInSeason_throwException() {
        Season otherSeason = Season.builder().id(2L).build();
        episode.setSeason(otherSeason);

        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));

        assertThrows(EpisodeNotInSeasonException.class,
                () -> episodeService.deleteEpisode(1L,1L));
    }
}
