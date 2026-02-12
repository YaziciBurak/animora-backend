package com.animora.favorite.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class FavoriteForbiddenException extends BusinessException {

    public FavoriteForbiddenException() {
        super(
                HttpStatus.FORBIDDEN,
                ErrorCode.FORBIDDEN,
                ErrorMessage.FAVORITE_FORBIDDEN
        );
    }
}
