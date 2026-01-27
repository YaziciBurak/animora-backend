package com.animora.episode.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class EpisodeNotInSeasonException extends BusinessException {

    public EpisodeNotInSeasonException(Long episodeId, Long seasonId) {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.EPISODE_NOT_IN_SEASON,
                "Episode " + episodeId + " does not belong to season " + seasonId
        );
    }
}
