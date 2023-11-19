package com.gdsc_teamb.servertoyproject.domain.response.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private int code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldError> errors;

}