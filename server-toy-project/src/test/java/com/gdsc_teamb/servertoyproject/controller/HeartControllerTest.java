package com.gdsc_teamb.servertoyproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc_teamb.servertoyproject.domain.post.domain.HeartEntity;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.repository.HeartRepository;
import com.gdsc_teamb.servertoyproject.domain.repository.PostRepository;
import com.gdsc_teamb.servertoyproject.domain.repository.UserRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardDto;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardUpdateDto;
import com.gdsc_teamb.servertoyproject.dto.likeDto.HeartDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class HeartControllerTest {
    // 테스트에 사용할 서버의 랜덤 포트 번호를 할당
    @LocalServerPort
    private int port;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HeartRepository heartRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("좋아요 등록 테스트")
    public void registerHeart() throws Exception{
        //Given 등록할 좋아요 생성
        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .email("abc@abc.com")
                .password("password1234")
                .nickname("nickname")
                .phone("01012345678")
                .build());

        PostEntity savedPost = postRepository.save(PostEntity.builder()
                .title("title-test")
                .content("content-test")
                .user(savedUser)
                .build());

        HeartDto heartDto = HeartDto.builder()
                .user(savedUser)
                .post(savedPost)
                .build();

        // 게시물 id
        Long Id = savedPost.getId();
        String url="http://localhost:" + port + "/api/heart/"+Id;

        // when
        mockMvc.perform(post(url)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(heartDto)))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        // 저장한 데이터만 조회
        Optional<HeartEntity> savedHeart = heartRepository
                .findByUserIdAndPostId(savedUser.getId(), savedPost.getId());

        assertThat(savedHeart).isPresent(); // Optional이 존재하는지 확인
        assertThat(savedHeart.get().getUser().getId()).isEqualTo(savedUser.getId()); // User id 확인
        assertThat(savedHeart.get().getPost().getId()).isEqualTo(savedPost.getId()); // Post id 확인
    }

    @Test
    @DisplayName("좋아요 삭제 테스트")
    public void deleteHeart() throws Exception{
        //Given 삭제할 좋아요 생성
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

        HeartEntity savedHeart = heartRepository.save(HeartEntity.builder()
                .user(savedUser)
                .post(savedPost)
                .build());

        // 게시물 id
        Long Id = savedPost.getId();
        String url="http://localhost:" + port + "/api/heart/"+Id;

        // when
        mockMvc.perform(delete(url))
                .andExpect(status().isOk());

        // then
        // 삭제한 데이터 조회
        Optional<HeartEntity> deletedHeart = heartRepository.findById(savedHeart.getId());
        assertThat(deletedHeart).isNotPresent(); // 삭제된 경우 Optional이 비어있어야 함
    }
}