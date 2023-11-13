package com.gdsc_teamb.servertoyproject.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
// 클라이언트에 토큰을 보내기 위한 DTO
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}