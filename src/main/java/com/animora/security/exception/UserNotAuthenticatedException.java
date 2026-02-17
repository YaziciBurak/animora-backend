package com.animora.security.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class UserNotAuthenticatedException extends BusinessException {
    public UserNotAuthenticatedException() {
        super(
                HttpStatus.UNAUTHORIZED,
                ErrorCode.USER_NOT_AUTHENTICATED,
                ErrorMessage.USER_NOT_AUTHENTICATED
        );
    }
}
