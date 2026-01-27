package com.animora.anime.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class AnimeAlreadyExistsException extends BusinessException {

    public AnimeAlreadyExistsException(String title) {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.ANIME_ALREADY_EXISTS,
                "Anime already exists with title: " + title
        );
    }
}
