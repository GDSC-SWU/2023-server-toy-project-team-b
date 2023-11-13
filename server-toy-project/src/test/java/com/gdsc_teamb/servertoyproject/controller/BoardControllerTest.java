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


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    // 테스트 실행 후에 수행될 데이터 정리 메서드
    @AfterEach
    public void tearDown() throws Exception {
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }

    final String TITLE = "title-test";
    final String CONTENT = "content-test";

    @Test
    public void Posts_등록() throws Exception{
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

        // When 게시글 등록 요청
        // 실제로 게시글을 등록하는 HTTP POST 요청을 보냄
        // TestRestTemplate 을 사용하여 지정된 URL에 boardDto를 POST 방식으로 전송
        // ResponseEntity<Long> 은 HTTP 응답을 나타내는 객체
        // 이 ResponseEntity 는 HTTP 응답 상태 코드와 함께 생성된 게시글의 ID를 포함 (responseEntity.getBody()를 통해 반환 가능)
        ResponseEntity<Long> responseEntity=restTemplate.postForEntity(url, boardDto, Long.class);

        // Then 게시글 등록
        // 응답 상태 코드가 HttpStatus.OK인지 확인
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // 반환된 게시글의 ID가 0보다 큰지 확인
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // 데이터베이스에 저장된 모든 게시글을 조회하고, 첫 번째 게시글이 기대한 대로 생성되었는지 검증
        List<PostEntity> all = boardRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(TITLE);
        assertThat(all.get(0).getContent()).isEqualTo(CONTENT);
        assertThat(all.get(0).getUser().getId()).isEqualTo(savedUser.getId());

    }

    @Test
    public void Posts_수정() throws Exception {
        // Given 등록된 게시물과 수정할 게시물
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

        // When 게시글 수정 요청
        ResponseEntity<Long> responseEntity = restTemplate.
                exchange(url, HttpMethod.PUT,
                        requestEntity, Long.class);

        // Then 게시글 수정된다
        List<PostEntity> all = boardRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    public void Posts_조회() throws Exception{

    }

    @Test
    public void Posts_목록조회() throws Exception{

    }

    @Test
    public void Posts_삭제() throws Exception{

    }
}