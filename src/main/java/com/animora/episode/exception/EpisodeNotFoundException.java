package com.animora.episode.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class EpisodeNotFoundException extends BusinessException {

    public EpisodeNotFoundException(Long episodeId) {
        super(
                HttpStatus.NOT_FOUND,
                ErrorCode.EPISODE_NOT_FOUND,
                ErrorMessage.EPISODE_NOT_FOUND,
                episodeId
        );
    }
}
