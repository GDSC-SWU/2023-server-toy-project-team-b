package com.gdsc_teamb.servertoyproject.domain.comment.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.request.NewCommentReqDto;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostRepository;
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

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private Long postId;

    @BeforeEach
    void addMockData() {
        UserEntity mockUser = UserEntity.builder()
                .email("test@test.com")
                .nickname("user01")
                .password("password")
                .phone("01011111111")
                .build();
        userRepository.save(mockUser);

        PostEntity post = PostEntity.builder()
                .user(mockUser)
                .title("test-title")
                .content("test-content")
                .build();

        postId = postRepository.save(post).getId();
    }

    @Test
    @DisplayName("addComment() API 테스트")
    void addComment() throws Exception {
        // given
        String content = "test-comment";
        NewCommentReqDto reqDto = new NewCommentReqDto(content);
        String json = new ObjectMapper().writeValueAsString(reqDto);

        // when, then
        mockMvc.perform(post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.postId").value(equalTo(postId.intValue())))
                .andExpect(jsonPath("$.data.content").value(equalTo(content)))
                .andExpect(jsonPath("$.message").value(equalTo(HttpStatus.CREATED.getReasonPhrase())))
                .andDo(print());
    }
}