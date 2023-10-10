package com.gdsc_teamb.servertoyproject.domain.user.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("User Entity Test")
@Slf4j
class UserEntityTest {
    final String EMAIL = "abc@abc.com";
    final String PASSWORD = "password1234";
    final String NICKNAME = "nickname";
    final String PHONE = "010-1234-5678";

    @Test
    @DisplayName("User equals() true 테스트")
    void userEqualsTrue() {
        // given
        String password = "1234password";
        String phone = "010-1111-2222";

        UserEntity userA = UserEntity.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .phone(PHONE)
                .build();

        UserEntity userB = UserEntity.builder()
                .email(EMAIL)
                .password(password)
                .nickname(NICKNAME)
                .phone(phone)
                .build();

        // when
        Boolean isEqualUser = userA.equals(userB);

        // then
        assertThat(isEqualUser).as("[Test failed] email과 nickname이 동일한 경우 UserEntity 동치 검사 실패").isTrue();

        log.info("[Test passed] email과 nickname이 동일한 경우 UserEntity 동치");
    }

    @Test
    @DisplayName("User equals() false 테스트")
    void userEqualsFalse() {
        // given
        String email = "def@abc.com";

        UserEntity userA = UserEntity.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .phone(PHONE)
                .build();

        UserEntity userB = UserEntity.builder()
                .email(email)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .phone(PHONE)
                .build();

        // when
        Boolean isEqualUser = userA.equals(userB);

        // then
        assertThat(isEqualUser).as("[Test failed] email이 다른 경우 UserEntity 동치 미성립 검사 실패").isFalse();

        log.info("[Test passed] email이 다른 경우 UserEntity 동치 미성립");
    }
}