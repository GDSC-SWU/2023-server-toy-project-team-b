package com.gdsc_teamb.servertoyproject.web.dto;

import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserJoinRequestDto {

    private String email;
    private String password;
    private String nickname;
    private String phone;

    @Builder
    public UserJoinRequestDto(String email,String password,String nickname,String phone){
        this.email=email;
        this.password=password;
        this.nickname=nickname;
        this.phone=phone;
    }

    public UserEntity toEntity(){
        return UserEntity.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .phone(phone)
                .build();
    }
}
