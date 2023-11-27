package com.gdsc_teamb.servertoyproject.domain.post.domain;

import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Like Entity Test")
@Slf4j
class HeartEntityTest {
    final UserEntity USER = UserEntity.builder()
            .email("abc@abc.com")
            .password("password1234")
            .nickname("nickname")
            .phone("010-1234-5678")
            .build();
    final PostEntity POST = PostEntity.builder()
            .user(USER)
            .title("title-test")
            .content("content-test")
            .build();

    @Test
    @DisplayName("LikeEntity equals() true 테스트")
    void likeEqualsTrue() {
        // given
        HeartEntity likeA = new HeartEntity(USER, POST);
        HeartEntity likeB = new HeartEntity(USER, POST);

        // when
        Boolean isLikeEqual = likeA.equals(likeB);

        // then
        assertThat(isLikeEqual).as("[Test failed] user, post가 같을 경우 LikeEntity 동치 테스트 실패").isTrue();

        log.info("[Test passed] user, post가 같을 경우 LikeEntity 동치");
    }

}