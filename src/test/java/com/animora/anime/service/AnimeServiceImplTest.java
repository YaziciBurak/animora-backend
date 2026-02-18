package com.animora.anime.service;

import com.animora.anime.dto.AnimeRequest;
import com.animora.anime.dto.AnimeResponse;
import com.animora.anime.entity.Anime;
import com.animora.anime.exception.AnimeAlreadyExistsException;
import com.animora.anime.exception.AnimeHasSeasonsException;
import com.animora.anime.mapper.AnimeMapper;
import com.animora.anime.repository.AnimeRepository;
import com.animora.anime.service.impl.AnimeServiceImpl;
import com.animora.season.entity.Season;
import com.animora.season.repository.SeasonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AnimeServiceImplTest {

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private SeasonRepository seasonRepository;

    @Mock
    private AnimeMapper animeMapper;

    @InjectMocks
    private AnimeServiceImpl animeService;

    private Anime anime;
    private AnimeRequest request;
    private AnimeResponse response;

    @BeforeEach
    void setUp() {
        anime = Anime.builder()
                .id(1L)
                .title("Naruto")
                .build();

        request = AnimeRequest.builder()
                .title("Naruto")
                .build();

        response = AnimeResponse.builder()
                .id(1L)
                .title("Naruto")
                .build();
    }

    @Test
    void createAnime_success() {
        when(animeRepository.existsByTitleIgnoreCase("Naruto")).thenReturn(false);
        when(animeMapper.toEntity(request)).thenReturn(anime);
        when(animeRepository.save(anime)).thenReturn(anime);
        when(seasonRepository.existsByAnimeAndSeasonNumber(anime, 1)).thenReturn(false);
        when(animeMapper.toResponse(anime)).thenReturn(response);

        AnimeResponse result = animeService.createAnime(request);

        assertEquals("Naruto", result.getTitle());
        verify(animeRepository).save(anime);
        verify(seasonRepository).save(any(Season.class));
    }

    @Test
    void createAnime_whenTitleExists_throwException() {
        when(animeRepository.existsByTitleIgnoreCase("Naruto")).thenReturn(true);

        assertThrows(AnimeAlreadyExistsException.class,
                () -> animeService.createAnime(request));
    }

    @Test
    void getAnimeById_success() {
        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(animeMapper.toResponse(anime)).thenReturn(response);

        AnimeResponse result = animeService.getAnimeById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void updateAnime_success() {

        anime.setTitle("Naruto");

        AnimeRequest updateRequest = AnimeRequest.builder()
                .title("One piece")
                .build();

        Anime updatedAnime = Anime.builder()
                .id(1L)
                .title("One piece")
                .build();

        AnimeResponse updatedResponse = AnimeResponse.builder()
                .id(1L)
                .title("One piece")
                .build();

        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(animeRepository.existsByTitleIgnoreCaseAndIdNot("One piece", 1L)).thenReturn(false);
        when(animeRepository.save(anime)).thenReturn(updatedAnime);
        when(animeMapper.toResponse(updatedAnime)).thenReturn(updatedResponse);

        doAnswer(invocation -> {
            anime.setTitle("One piece");
            return null;
        }).when(animeMapper).updateEntityFromRequest(updateRequest, anime);

        AnimeResponse result = animeService.updateAnime(1L, updateRequest);

        assertEquals("One piece", result.getTitle());
        verify(animeMapper).updateEntityFromRequest(updateRequest, anime);
    }

    @Test
    void updateAnime_whenDuplicateTitle_throwException() {
        anime.setTitle("Naruto");

        AnimeRequest updateRequest = AnimeRequest.builder()
                .title("One piece")
                .build();

        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(animeRepository.existsByTitleIgnoreCaseAndIdNot("One piece", 1L)).thenReturn(true);

        assertThrows(AnimeAlreadyExistsException.class,
                () -> animeService.updateAnime(1L, updateRequest));
    }

    @Test
    void deleteAnime_success() {
        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(seasonRepository.existsByAnime_Id(1L)).thenReturn(false);

        animeService.deleteAnime(1L);

        verify(animeRepository).delete(anime);
    }

    @Test
    void deleteAnime_whenHasSeasons_throwException() {
        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(seasonRepository.existsByAnime_Id(1L)).thenReturn(true);

        assertThrows(AnimeHasSeasonsException.class,
                () -> animeService.deleteAnime(1L));
    }
}
