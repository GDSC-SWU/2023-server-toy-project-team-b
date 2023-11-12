package com.gdsc_teamb.servertoyproject.login;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String memberId;
    private String password;
}