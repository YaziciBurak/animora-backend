package com.animora.genre.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class GenreAlreadyExistsException extends BusinessException {

    public GenreAlreadyExistsException(String name) {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.GENRE_ALREADY_EXISTS,
                ErrorMessage.GENRE_ALREADY_EXISTS,
                name
        );
    }
}
