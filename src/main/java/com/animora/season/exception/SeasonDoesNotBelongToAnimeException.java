package com.animora.season.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class SeasonDoesNotBelongToAnimeException extends BusinessException {

    public SeasonDoesNotBelongToAnimeException(Long animeId, Long seasonId) {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.SEASON_DOES_NOT_BELONG_TO_ANIME,
                ErrorMessage.SEASON_DOES_NOT_BELONG_TO_ANIME,
                animeId,
                seasonId
        );
    }
}
