package com.gdsc_teamb.servertoyproject.domain.comment.domain;

import com.gdsc_teamb.servertoyproject.domain.comment.dto.CommentItemDto;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.repository.PostRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Comment DAO 테스트")
@Transactional
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    private UserEntity user;
    private PostEntity post;

    @BeforeEach
    void addMockData() {
        user = UserEntity.builder()
                .email("test@test.com")
                .nickname("user01")
                .password("password")
                .phone("01011111111")
                .build();
        userRepository.save(user);

        post = PostEntity.builder()
                .user(user)
                .title("test-title")
                .content("test-content")
                .build();
        postRepository.save(post);
    }

    @Test
    @DisplayName("findAllByPost() 테스트")
    void findAllByPost() {
        // given
        String content1 = "comment1-test";
        String content2 = "comment2-test";
        CommentEntity comment1 = CommentEntity.builder()
                .user(user)
                .post(post)
                .content(content1)
                .build();
        commentRepository.save(comment1);
        CommentEntity comment2 = CommentEntity.builder()
                .user(user)
                .post(post)
                .content(content2)
                .build();
        commentRepository.save(comment2);

        // when
        ArrayList<CommentItemDto> comments = commentRepository.findAllByPost(post);

        // then
        assertThat(comments.size()).as("데이터 개수가 2건이 아님.").isEqualTo(2);
        assertThat(comments.get(0).getContent()).as("DB에 저장된 content가 올바르지 않음.").isEqualTo(content1);
        assertThat(comments.get(1).getContent()).as("DB에 저장된 content가 올바르지 않음.").isEqualTo(content2);
    }
}