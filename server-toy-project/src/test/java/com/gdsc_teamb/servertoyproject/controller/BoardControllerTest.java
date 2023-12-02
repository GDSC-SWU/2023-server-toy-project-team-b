package com.gdsc_teamb.servertoyproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc_teamb.servertoyproject.domain.post.domain.HeartEntity;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.repository.PostRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardDto;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardUpdateDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class BoardControllerTest {
    // 테스트에 사용할 서버의 랜덤 포트 번호를 할당
    @LocalServerPort
    private int port;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("게시물 등록 테스트")
    public void registerPost() throws Exception{
        final String TITLE = "title-test";
        final String CONTENT = "content-test";

        //Given 등록할 게시글 생성
        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .email("abc@abc.com")
                .password("password1234")
                .nickname("nickname")
                .phone("01012345678")
                .build());

        BoardDto boardDto=BoardDto.builder()
                .title(TITLE)
                .content(CONTENT)
                .user(savedUser)
                .build();

        String url="http://localhost:" + port + "/api/boards";

        // when
        mockMvc.perform(post(url)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(boardDto)))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        // 저장한 데이터만 조회
        List<PostEntity> savedPosts = postRepository.findAllByUser(savedUser);
        assertThat(savedPosts).hasSize(1); // 사용자에게 속하는 게시글 중에서 검사
        PostEntity postSaved = savedPosts.get(0);

        assertThat(postSaved.getTitle()).isEqualTo(TITLE);
        assertThat(postSaved.getContent()).isEqualTo(CONTENT);
        assertThat(postSaved.getUser().getId()).isEqualTo(savedUser.getId());

    }

    @Test
    @DisplayName("게시물 수정 테스트")
    public void updatePost() throws Exception {
        // Given 등록된 게시물과 수정할 게시물
        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .email("abc@abc.com")
                .password("password1234")
                .nickname("nickname")
                .phone("01012345678")
                .build());

        PostEntity savedPost = postRepository.save(PostEntity.builder()
                .title("title")
                .content("content")
                .user(savedUser)
                .build());

        Long updateId = savedPost.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        BoardUpdateDto boardUpdateDto= BoardUpdateDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url="http://localhost:" + port + "/api/boards/"+updateId;

        // when
        mockMvc.perform(put(url)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(boardUpdateDto)))
                .andExpect(status().isOk());

        // then
        // 저장한 데이터만 조회
        List<PostEntity> savedPosts = postRepository.findAllByUser(savedUser);
        assertThat(savedPosts).hasSize(1); // 사용자에게 속하는 게시글 중에서 검사
        PostEntity postSaved = savedPosts.get(0);

        assertThat(postSaved.getTitle()).isEqualTo(expectedTitle);
        assertThat(postSaved.getContent()).isEqualTo(expectedContent);

    }

    @Test
    @DisplayName("게시물 삭제 테스트")
    public void deletePost() throws Exception{
        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .email("abc@abc.com")
                .password("password1234")
                .nickname("nickname")
                .phone("01012345678")
                .build());

        PostEntity savedPost = postRepository.save(PostEntity.builder()
                .title("title")
                .content("content")
                .user(savedUser)
                .build());

        Long deleteId = savedPost.getId();
        String url="http://localhost:" + port + "/api/boards/"+deleteId;

        // when
        mockMvc.perform(delete(url))
                .andExpect(status().isOk());

        // then
        // 삭제한 데이터 조회
        Optional<PostEntity> deletedPost = postRepository.findById(savedPost.getId());
        assertThat(deletedPost).isNotPresent();
    }
}