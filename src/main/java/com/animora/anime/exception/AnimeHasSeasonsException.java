package com.animora.anime.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class AnimeHasSeasonsException extends BusinessException {

    public AnimeHasSeasonsException(Long animeId) {
        super(
                HttpStatus.CONFLICT,
                ErrorCode.ANIME_HAS_SEASONS,
                "Anime has seasons and cannot be deleted. Anime id: " + animeId
                );
    }
}
