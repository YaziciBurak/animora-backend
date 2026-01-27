package com.animora.common.exception.dto;

import com.animora.common.exception.enums.ErrorCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class ValidationErrorResponse {

    private int status;
    private ErrorCode error;
    private String path;
    private LocalDateTime timeStamp;

    private Map<String, String> validationErrors;
}
