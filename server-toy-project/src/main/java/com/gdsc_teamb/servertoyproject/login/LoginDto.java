package com.gdsc_teamb.servertoyproject.login;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class LoginDto {
    private String memberId;
    private String password;
}