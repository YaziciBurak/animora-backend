package com.animora.anime.service;

import com.animora.anime.dto.AnimeDetailResponse;
import com.animora.anime.dto.AnimeRequest;
import com.animora.anime.dto.AnimeResponse;

import java.util.List;

public interface AnimeService {

    AnimeResponse createAnime(AnimeRequest request);

    AnimeResponse getAnimeById(Long id);

    List<AnimeResponse> getAllAnime();

    AnimeResponse updateAnime(Long id, AnimeRequest request);

    AnimeDetailResponse getAnimeDetail(Long animeId);

    void deleteAnime(Long id);
}
