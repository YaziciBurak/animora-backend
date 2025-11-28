package com.animora.genre.service;

import com.animora.genre.dto.GenreRequest;
import com.animora.genre.dto.GenreResponse;

import java.util.List;

public interface GenreService {

    GenreResponse createGenre(GenreRequest request);

    GenreResponse getGenreById(Long id);

    List<GenreResponse> getAllGenres();

    GenreResponse updateGenre(Long id, GenreRequest request);

    void deleteGenre(Long id);
}
