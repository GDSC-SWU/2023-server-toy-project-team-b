package com.gdsc_teamb.servertoyproject.dto;

import com.gdsc_teamb.servertoyproject.web.dto.UserJoinRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserJoinRequestDtoTest {

    @Test
    public void Test_Lombok(){
        // given
        String email = "abc";
        String password = "passw";
        String nickname = "nie";
        String phone = "01-5678";

        // when
        UserJoinRequestDto requestDto = new UserJoinRequestDto(email,password,nickname,phone);

        // then
        assertThat(requestDto.getEmail()).isEqualTo(email);
        assertThat(requestDto.getPassword()).isEqualTo(password);
        assertThat(requestDto.getNickname()).isEqualTo(nickname);
        assertThat(requestDto.getPhone()).isEqualTo(phone);

    }
}
