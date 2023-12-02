package com.gdsc_teamb.servertoyproject.domain.comment.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc_teamb.servertoyproject.domain.comment.domain.CommentEntity;
import com.gdsc_teamb.servertoyproject.domain.comment.domain.CommentRepository;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.request.NewCommentReqDto;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.repository.PostRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DisplayName("Comment API 테스트 Without Spring Security")
@AutoConfigureMockMvc
@Transactional
class CommentApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

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
    @DisplayName("addComment() API 테스트")
    void addComment() throws Exception {
        // given
        String url = "/comment/" + post.getId();
        String content = "test-comment";
        NewCommentReqDto reqDto = new NewCommentReqDto(content);
        String json = new ObjectMapper().writeValueAsString(reqDto);

        // when, then
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.postId").value(equalTo(post.getId().intValue())))
                .andExpect(jsonPath("$.data.content").value(equalTo(content)))
                .andExpect(jsonPath("$.message").value(equalTo(HttpStatus.CREATED.getReasonPhrase())))
                .andDo(print());
    }

    @Test
    @DisplayName("readComment() API 테스트")
    void readComment() throws Exception {
        // given
        String url = "/comment/" + post.getId();
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

        // when, then
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(equalTo(2)))
                .andExpect(jsonPath("$.data.postId").value(equalTo(post.getId().intValue())))
                .andExpect(jsonPath("$.data.comments[0].nickname").value(equalTo(user.getNickname())))
                .andExpect(jsonPath("$.data.comments[1].nickname").value(equalTo(user.getNickname())))
                .andExpect(jsonPath("$.data.comments[0].content").value(equalTo(content1)))
                .andExpect(jsonPath("$.data.comments[1].content").value(equalTo(content2)))
                .andDo(print());
    }

    @Test
    @DisplayName("updateComment() API 테스트")
    void updateComment() throws Exception {
        // given
        String content1 = "comment1-test";
        String content2 = "comment2-test";
        CommentEntity comment = CommentEntity.builder()
                .user(user)
                .post(post)
                .content(content1)
                .build();
        Long commentId = commentRepository.save(comment).getId();
        String url = "/comment/" + commentId;
        NewCommentReqDto reqDto = new NewCommentReqDto(content2);
        String json = new ObjectMapper().writeValueAsString(reqDto);

        // when, then
        mockMvc.perform(patch(url)
                        .param("temp", String.valueOf(user.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.writerNickname").value(equalTo(user.getNickname())))
                .andExpect(jsonPath("$.data.commentId").value(equalTo(commentId.intValue())))
                .andExpect(jsonPath("$.data.content").value(equalTo(content2)))
                .andDo(print());
    }

    @Test
    @DisplayName("deleteComment() API 테스트")
    void deleteComment() throws Exception {
        // given
        String content = "comment1-test";
        CommentEntity comment = CommentEntity.builder()
                .user(user)
                .post(post)
                .content(content)
                .build();
        Long commentId = commentRepository.save(comment).getId();
        String url = "/comment/" + commentId;

        // when, then
        mockMvc.perform(delete(url)
                        .param("temp", String.valueOf(user.getId())))
                .andExpect(status().isOk())
                .andDo(print());
        assertThat(commentRepository.findById(commentId).orElse(null)).as("데이터가 삭제되지 않음.").isNull();
    }
}