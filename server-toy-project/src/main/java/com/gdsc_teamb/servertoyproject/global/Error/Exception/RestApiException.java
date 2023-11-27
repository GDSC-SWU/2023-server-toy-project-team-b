package com.gdsc_teamb.servertoyproject.global.Error.Exception;

import com.gdsc_teamb.servertoyproject.global.Error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestApiException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public RestApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
