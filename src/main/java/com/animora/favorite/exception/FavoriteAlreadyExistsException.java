package com.animora.favorite.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class FavoriteAlreadyExistsException extends BusinessException {

    public FavoriteAlreadyExistsException(Long userId, Long animeId) {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.FAVORITE_ALREADY_EXISTS,
                ErrorMessage.FAVORITE_ALREADY_EXISTS,
                userId,
                animeId
        );
    }
}
