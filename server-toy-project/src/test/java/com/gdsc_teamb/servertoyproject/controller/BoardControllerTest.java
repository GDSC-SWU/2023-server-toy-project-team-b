package com.gdsc_teamb.servertoyproject.controller;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.repository.BoardRepository;
import com.gdsc_teamb.servertoyproject.domain.repository.UserRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.dto.BoardDto;
import com.gdsc_teamb.servertoyproject.dto.BoardUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoardControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() throws Exception {
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }

    final String TITLE = "title-test";
    final String CONTENT = "content-test";

    @Test
    public void Posts_등록() throws Exception{
        //given_등록할_게시글

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

        //when_게시글_등록요청
        ResponseEntity<Long> responseEntity=restTemplate.postForEntity(url, boardDto, Long.class);

        //then_게시글이_등록된다
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<PostEntity> all = boardRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(TITLE);
        assertThat(all.get(0).getContent()).isEqualTo(CONTENT);
        assertThat(all.get(0).getUser().getId()).isEqualTo(savedUser.getId());

    }

    @Test
    public void Posts_수정() throws Exception {
        //given 등록된 게시물과 수정할 게시물
        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .email("abc@abc.com")
                .password("password1234")
                .nickname("nickname")
                .phone("01012345678")
                .build());

        PostEntity savedPost = boardRepository.save(PostEntity.builder()
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

        // when 게시글 수정 요청
        ResponseEntity<Long> responseEntity = restTemplate.
                exchange(url, HttpMethod.PUT,
                        requestEntity, Long.class);

        // then 게시글 수정된다
        List<PostEntity> all = boardRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}