package com.animora.comment.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class CommentDeleteForbiddenException extends BusinessException {

    public CommentDeleteForbiddenException(Long commentId) {
        super(
                HttpStatus.FORBIDDEN,
                ErrorCode.FORBIDDEN,
                ErrorMessage.FORBIDDEN_COMMENT_DELETE,
                commentId
        );
    }
}
