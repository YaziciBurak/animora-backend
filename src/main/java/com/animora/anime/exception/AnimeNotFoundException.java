package com.animora.anime.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class AnimeNotFoundException extends BusinessException {

    public AnimeNotFoundException(Long animeId) {
        super(
                HttpStatus.NOT_FOUND,
                ErrorCode.ANIME_NOT_FOUND,
                ErrorMessage.ANIME_NOT_FOUND,
                animeId
        );
    }
}
