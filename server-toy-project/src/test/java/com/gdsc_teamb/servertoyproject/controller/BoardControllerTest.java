package com.gdsc_teamb.servertoyproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.repository.PostRepository;
import com.gdsc_teamb.servertoyproject.domain.repository.UserRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardDto;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BoardControllerTest {

    // 테스트에 사용할 서버의 랜덤 포트 번호를 할당
    @LocalServerPort
    private int port;

    // TestRestTemplate : 테스트용 RestTemplate
    // HTTP 요청을 수행하는 데 사용
    // 테스트 코드에서 HTTP 요청을 보내고 응답을 확인하는 등의 작업을 간편하게 수행할 수 있음
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    // 테스트 실행 후에 수행될 데이터 정리 메서드
    @AfterEach
    public void tearDown() throws Exception {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    final String TITLE = "title-test";
    final String CONTENT = "content-test";

    @Test
    @DisplayName("Post 등록 테스트")
    public void postRegister() throws Exception{
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(boardDto)))
                .andExpect(status().isOk());

        // then
        // 데이터베이스에 저장된 모든 게시글을 조회하고, 첫 번째 게시글이 기대한 대로 생성되었는지 검증
        List<PostEntity> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(TITLE);
        assertThat(all.get(0).getContent()).isEqualTo(CONTENT);
        assertThat(all.get(0).getUser().getId()).isEqualTo(savedUser.getId());

    }

    @Test
    @DisplayName("Post 수정 테스트")
    public void postUpdate() throws Exception {
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

        HttpEntity<BoardUpdateDto> requestEntity=new HttpEntity<>(boardUpdateDto);

        // when
        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(boardUpdateDto)))
                .andExpect(status().isOk());

        // then
        List<PostEntity> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    @DisplayName("Post 조회 테스트")
    public void checkPost() throws Exception{

    }

    @Test
    @DisplayName("Post 목록 조회 테스트")
    public void checkListPost() throws Exception{

    }

    @Test
    @DisplayName("Post 삭제 테스트")
    public void deletePost() throws Exception{

    }
}