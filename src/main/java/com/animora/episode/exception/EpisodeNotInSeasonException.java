package com.animora.episode.exception;

import com.animora.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class EpisodeNotInSeasonException extends BusinessException {

    public EpisodeNotInSeasonException(Long episodeId, Long seasonId) {
        super(
                HttpStatus.BAD_REQUEST,
                "EPISODE_NOT_IN_SEASONS",
                "Episode " + episodeId + " does not belong to season " + seasonId
        );
    }
}
