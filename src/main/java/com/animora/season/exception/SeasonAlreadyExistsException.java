package com.animora.season.exception;

import com.animora.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class SeasonAlreadyExistsException extends BusinessException {

    public SeasonAlreadyExistsException(int seasonNumber) {
        super(
                HttpStatus.BAD_REQUEST,
                "SEASON_ALREADY_EXISTS",
                "Season already exists: " + seasonNumber
        );
    }
}
