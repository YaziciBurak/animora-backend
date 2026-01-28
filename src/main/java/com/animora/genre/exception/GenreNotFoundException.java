package com.animora.genre.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class GenreNotFoundException extends BusinessException {

    public GenreNotFoundException(Long genreId) {
        super(
                HttpStatus.NOT_FOUND,
                ErrorCode.GENRE_NOT_FOUND,
                ErrorMessage.GENRE_NOT_FOUND,
                genreId
        );
    }
}
