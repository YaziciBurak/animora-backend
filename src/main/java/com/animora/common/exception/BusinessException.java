package com.animora.common.exception;

import com.animora.common.exception.enums.ErrorCode;
import com.animora.common.exception.enums.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException{

    private final HttpStatus httpStatus;

    private final ErrorCode errorCode;

    protected BusinessException(HttpStatus httpStatus, ErrorCode errorCode, ErrorMessage errorMessage, Object... args) {
        super(String.format(errorMessage.getMessage(), args));
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

}
