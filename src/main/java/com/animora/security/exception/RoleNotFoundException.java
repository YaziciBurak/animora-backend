package com.animora.security.exception;

import com.animora.common.exception.BusinessException;
import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends BusinessException {
    public RoleNotFoundException(String roleName) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.ROLE_NOT_FOUND,
                ErrorMessage.ROLE_NOT_FOUND,
                roleName
        );
    }
}
