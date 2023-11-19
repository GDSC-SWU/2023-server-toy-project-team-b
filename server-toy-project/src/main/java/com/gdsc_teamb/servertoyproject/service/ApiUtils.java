package com.gdsc_teamb.servertoyproject.service;

import com.gdsc_teamb.servertoyproject.domain.response.domain.CommonResponse;
import jakarta.annotation.Nullable;

public class ApiUtils {
    public static <T> CommonResponse<T> success(int code, @Nullable T result) {
        return new CommonResponse<>(code, true, result);
    }
}
