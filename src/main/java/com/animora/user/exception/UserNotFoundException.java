package com.animora.user.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(Long userId) {
        super(
                HttpStatus.NOT_FOUND,
                ErrorCode.USER_NOT_FOUND,
                ErrorMessage.USER_NOT_FOUND,
                userId
        );
    }
}
