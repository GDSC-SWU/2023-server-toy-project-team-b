package com.gdsc_teamb.servertoyproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppException extends RuntimeException{

    private ErrorCode errorCode;
}
