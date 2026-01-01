package com.animora.anime.service;

import java.util.List;

public interface AnimeGenreService {

    void addGenresToAnime(Long animeId, List<Long> genreIds);
}
