package com.animora.favorite.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class FavoriteNotFoundException extends BusinessException {

    public FavoriteNotFoundException(Long animeId) {
        super(
                HttpStatus.NOT_FOUND,
                ErrorCode.FAVORITE_NOT_FOUND,
                ErrorMessage.FAVORITE_NOT_FOUND,
                animeId
        );
    }
}
