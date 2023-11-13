package com.gdsc_teamb.servertoyproject.domain.comment.service;

import com.gdsc_teamb.servertoyproject.domain.comment.domain.CommentEntity;
import com.gdsc_teamb.servertoyproject.domain.comment.domain.CommentRepository;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.request.NewCommentReqDto;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.response.NewCommentResDto;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DisplayName("Comment 서비스 테스트 Without Spring Security")
@Transactional
class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    private UserEntity user;
    private Long postId;
    private final String NICKNAME = "user01";

    @BeforeEach
    void addMockData() {
        user = UserEntity.builder()
                .email("test@test.com")
                .nickname(NICKNAME)
                .password("password")
                .phone("01011111111")
                .build();
        userRepository.save(user);

        PostEntity post = PostEntity.builder()
                .user(user)
                .title("test-title")
                .content("test-content")
                .build();

        postId = postRepository.save(post).getId();
    }

    @Test
    @DisplayName("addComment() 테스트")
    void addComment() {
        // given
        String content = "comment_test";
        NewCommentReqDto reqDto = new NewCommentReqDto(postId, content);

        // when
        NewCommentResDto resDto = commentService.addComment(user, reqDto);

        // then
        CommentEntity commentEntity = commentRepository.findById(resDto.getCommentId()).orElse(null);
        Assertions.assertThat(commentEntity).as("데이터베이스에 올바르게 저장되지 않음.").isNotNull();
        Assertions.assertThat(commentEntity.getContent()).as("content가 데이터베이스에 올바르게 저장되지 않음.").isEqualTo(content);
        Assertions.assertThat(resDto.getNickname()).as("nickname이 올바르지 않음.").isEqualTo(NICKNAME);
        Assertions.assertThat(resDto.getIsWriter()).as("Post 작성자와 동일 여부가 올바르지 않음.").isTrue();
        Assertions.assertThat(resDto.getPostId()).as("postId가 올바르지 않음.").isEqualTo(postId);
        Assertions.assertThat(resDto.getContent()).as("content가 올바르지 않음.").isEqualTo(content);
    }
}