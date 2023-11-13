package com.gdsc_teamb.servertoyproject.domain.comment.service;

import com.gdsc_teamb.servertoyproject.domain.comment.domain.CommentEntity;
import com.gdsc_teamb.servertoyproject.domain.comment.domain.CommentRepository;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.request.NewCommentReqDto;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.response.NewCommentResDto;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.response.ReadCommentResDto;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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
    private PostEntity post;
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

        post = PostEntity.builder()
                .user(user)
                .title("test-title")
                .content("test-content")
                .build();
        postRepository.save(post);
    }

    @Test
    @DisplayName("addComment() 테스트")
    void addComment() {
        // given
        String content = "comment_test";
        NewCommentReqDto reqDto = new NewCommentReqDto(post.getId(), content);

        // when
        NewCommentResDto resDto = commentService.addComment(user, reqDto);

        // then
        CommentEntity commentEntity = commentRepository.findById(resDto.getCommentId()).orElse(null);
        assertThat(commentEntity).as("데이터베이스에 올바르게 저장되지 않음.").isNotNull();
        assertThat(commentEntity.getContent()).as("content가 데이터베이스에 올바르게 저장되지 않음.").isEqualTo(content);
        assertThat(resDto.getNickname()).as("nickname이 올바르지 않음.").isEqualTo(NICKNAME);
        assertThat(resDto.getIsPostWriter()).as("Post 작성자와 동일 여부가 올바르지 않음.").isTrue();
        assertThat(resDto.getPostId()).as("postId가 올바르지 않음.").isEqualTo(post.getId());
        assertThat(resDto.getContent()).as("content가 올바르지 않음.").isEqualTo(content);
    }

    @Test
    @DisplayName("readComment() 테스트")
    void readComment() {
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
        ReadCommentResDto resDto = commentService.readComment(post.getId());

        // then
        assertThat(resDto.getPostId()).as("post-id가 올바르지 않음.").isEqualTo(post.getId());
        assertThat(resDto.getComments().size()).as("Comment 데이터 개수가 2건이 아님.").isEqualTo(2);
        assertThat(resDto.getComments().get(0).getContent()).as("content가 올바르지 않음.").isEqualTo(content1);
        assertThat(resDto.getComments().get(1).getContent()).as("content가 올바르지 않음.").isEqualTo(content2);
    }
}