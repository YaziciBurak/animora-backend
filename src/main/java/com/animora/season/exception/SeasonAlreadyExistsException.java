package com.animora.season.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class SeasonAlreadyExistsException extends BusinessException {

    public SeasonAlreadyExistsException(int seasonNumber) {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.SEASON_ALREADY_EXISTS,
                ErrorMessage.SEASON_ALREADY_EXISTS,
                seasonNumber
        );
    }
}
