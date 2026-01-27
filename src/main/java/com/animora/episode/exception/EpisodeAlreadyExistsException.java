package com.animora.episode.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class EpisodeAlreadyExistsException extends BusinessException {

    public EpisodeAlreadyExistsException(int episodeNumber) {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.EPISODE_ALREADY_EXISTS,
                "Episode already exists: " + episodeNumber
        );
    }
}
