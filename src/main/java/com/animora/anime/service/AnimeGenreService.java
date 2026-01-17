package com.animora.anime.service;

import java.util.List;

public interface AnimeGenreService {

    void createGenresToAnime(Long animeId, List<Long> genreIds);
}
