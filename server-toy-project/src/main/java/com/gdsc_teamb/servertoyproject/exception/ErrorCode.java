package com.gdsc_teamb.servertoyproject.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // 유저
    HANDLE_ACCESS_DENIED(403, "로그인이 필요합니다."),
    INVALID_INPUT_USERNAME(400, "닉네임을 3자 이상 입력하세요"),
    NOT_EQUAL_INPUT_PASSWORD(400,  "비밀번호가 일치하지 않습니다"),
    INVALID_PASSWORD(400,  "비밀번호를 4자 이상 입력하세요"),
    INVALID_USERNAME(400,  "알파벳 대소문자와 숫자로만 입력하세요"),
    NOT_AUTHORIZED(403, "권한이 없습니다."),
    USER_EMAIL_DUPLICATION(400, "이미 등록된 이메일입니다."),
    USER_NICKNAME_DUPLICATION(400, "이미 등록된 닉네임입니다."),
    NOT_FOUND_USER(404,  "해당 유저가 존재하지 않습니다.");

    @Getter
    private final String message;
    private final int status;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

}