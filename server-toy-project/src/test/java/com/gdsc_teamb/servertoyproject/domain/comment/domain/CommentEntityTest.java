package com.gdsc_teamb.servertoyproject.domain.comment.domain;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Comment Entity Test")
@Slf4j
class CommentEntityTest {
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
    final String CONTENT = "comment-content-test";

    @Test
    @DisplayName("CommentEntity equals() false 테스트")
    void commentEqualsTrue() {
        // given
        CommentEntity commentA = CommentEntity.builder()
                .user(USER)
                .post(POST)
                .content(CONTENT)
                .build();

        CommentEntity commentB = CommentEntity.builder()
                .user(USER)
                .post(POST)
                .content(CONTENT)
                .build();

        // when
        Boolean isCommentEqual = commentA.equals(commentB);

        // then
        assertThat(isCommentEqual).as("[Test failed] user, post, content가 같아도 id가 null일 경우 CommentEntity 동치 미성립 테스트 실패").isFalse();

        log.info("[Test passed] user, post, content가 같아도 id가 null일 경우 CommentEntity 동치 미성립");
    }
}