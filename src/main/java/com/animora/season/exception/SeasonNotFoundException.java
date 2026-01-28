package com.animora.season.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class SeasonNotFoundException extends BusinessException {

    public SeasonNotFoundException(Long seasonId) {
        super(
                HttpStatus.NOT_FOUND,
                ErrorCode.SEASON_NOT_FOUND,
                ErrorMessage.SEASON_NOT_FOUND,
                seasonId
        );
    }
}
