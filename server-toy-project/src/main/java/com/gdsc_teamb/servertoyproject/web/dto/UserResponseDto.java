package com.gdsc_teamb.servertoyproject.web.dto;

import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private String nickname;
    private String phone;

    public UserResponseDto(UserEntity user){
        this.email=user.getEmail();
        this.nickname=user.getNickname();
        this.phone=user.getPhone();
    }

}
