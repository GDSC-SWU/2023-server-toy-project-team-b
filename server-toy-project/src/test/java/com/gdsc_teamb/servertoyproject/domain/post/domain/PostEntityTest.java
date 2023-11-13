package com.gdsc_teamb.servertoyproject.domain.post.domain;

import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Post Entity Test")
@Slf4j
class PostEntityTest {
    final UserEntity USER = UserEntity.builder()
            .email("abc@abc.com")
            .password("password1234")
            .nickname("nickname")
            .phone("010-1234-5678")
            .build();
    final String TITLE = "title-test";
    final String CONTENT = "content-test";

    @Test
    @DisplayName("Post equals() false 테스트")
    void postEqualsTrue() {
        // given
        PostEntity postA = PostEntity.builder()
                .user(USER)
                .title(TITLE)
                .content(CONTENT)
                .build();

        PostEntity postB = PostEntity.builder()
                .user(USER)
                .title(TITLE)
                .content(CONTENT)
                .build();

        // when
        Boolean isPostEqual = postA.equals(postB);

        // then
        assertThat(isPostEqual).as("[Test failed] user, title, content가 같아도 id가 null이면 동치 미성립 검사 실패").isFalse();

        log.info("[Test passed] user, title, content가 같아도 id가 null이면 동치 미성립");
    }
}