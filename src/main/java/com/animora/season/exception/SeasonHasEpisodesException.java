package com.animora.season.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class SeasonHasEpisodesException extends BusinessException {

    public SeasonHasEpisodesException(Long seasonId) {
        super(
                HttpStatus.CONFLICT,
                ErrorCode.SEASON_HAS_EPISODES,
                "Season has episodes and cannot be deleted. Season id: " + seasonId
        );
    }
}
