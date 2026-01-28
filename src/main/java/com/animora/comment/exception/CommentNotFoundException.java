package com.animora.comment.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends BusinessException {

    public CommentNotFoundException(Long commentId) {
        super(
                HttpStatus.NOT_FOUND,
                ErrorCode.COMMENT_NOT_FOUND,
                ErrorMessage.COMMENT_NOT_FOUND,
                commentId
        );
    }
}
