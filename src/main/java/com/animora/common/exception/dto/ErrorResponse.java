package com.animora.common.exception.dto;

import com.animora.common.exception.enums.ErrorCode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private int status;
    private ErrorCode error;
    private String message;
    private String path;
    private LocalDateTime timeStamp;
}
