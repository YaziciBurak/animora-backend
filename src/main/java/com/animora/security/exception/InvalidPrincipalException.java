package com.animora.security.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class InvalidPrincipalException extends BusinessException {
    public InvalidPrincipalException() {
        super(
                HttpStatus.UNAUTHORIZED,
                ErrorCode.INVALID_PRINCIPAL_TYPE,
                ErrorMessage.INVALID_PRINCIPAL_TYPE
        );
    }
}
