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

    @Builder
    public UserResponseDto(String email,String nickname,String phone){
        this.email=email;
        this.nickname=nickname;
        this.phone=phone;
    }
    public UserEntity toEntity(){
        return UserEntity.builder()
                .email(email)
                .nickname(nickname)
                .phone(phone)
                .build();
    }
}
