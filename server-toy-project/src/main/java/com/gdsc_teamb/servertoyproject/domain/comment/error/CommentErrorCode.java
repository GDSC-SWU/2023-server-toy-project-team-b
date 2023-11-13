package com.gdsc_teamb.servertoyproject.domain.comment.error;

import com.gdsc_teamb.servertoyproject.global.Error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommentErrorCode implements ErrorCode {
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "Invalid Post Id.");

    private final HttpStatus httpStatus;
    private final String message;

    CommentErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
