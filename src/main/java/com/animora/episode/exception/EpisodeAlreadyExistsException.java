package com.animora.episode.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class EpisodeAlreadyExistsException extends BusinessException {

    public EpisodeAlreadyExistsException(int episodeNumber) {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.EPISODE_ALREADY_EXISTS,
                ErrorMessage.EPISODE_ALREADY_EXISTS,
                episodeNumber
        );
    }
}
