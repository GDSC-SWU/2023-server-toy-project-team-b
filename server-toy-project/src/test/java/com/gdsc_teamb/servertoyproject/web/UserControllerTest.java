package com.gdsc_teamb.servertoyproject.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gdsc_teamb.servertoyproject.ServerToyProjectApplication;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserRepository;
import com.gdsc_teamb.servertoyproject.service.UserService;
import com.gdsc_teamb.servertoyproject.web.dto.UpdatePasswordDto;
import com.gdsc_teamb.servertoyproject.web.dto.UserJoinRequestDto;
import com.gdsc_teamb.servertoyproject.web.dto.UserUpdateRequestDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@ContextConfiguration(classes = ServerToyProjectApplication.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("회원가입 성공")
    public void join_회원가입() throws Exception {

        //given
        String email = "testemail";
        String password = "testpwd";
        String nickname = "nktest";
        String phone = "999999";

        UserJoinRequestDto requestDto = UserJoinRequestDto.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .phone(phone)
                .build();

        String url = "http://localhost:" + port + "/api/v1/user/join";

        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(requestDto)))
                .andExpect(status().isOk());

        // then
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        assertThat(userOptional).isPresent(); // 이메일로 찾은 유저가 존재하는지 확인

        UserEntity user = userOptional.get(); // 유저 정보 가져오기
        // email, nickname, phone ,password 일치 여부 확인
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getPhone()).isEqualTo(phone);

        boolean isPasswordMatching = user.matchPassword(encoder, password);
        assertThat(isPasswordMatching).isTrue();

    }

    @Test
    @DisplayName("회원정보 nickname, phone 수정 성공")
    public void update_회원정보변경() throws Exception {

        //given
        String email = "testemail";
        String password = "testpwd";
        String nickname = "nktest";
        String phone = "999999";

        userService.join(email,nickname,password,phone);

        String newNickname = "newnick";
        String newPhone = "98765";

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("nickname", newNickname);
        requestBody.put("phone", newPhone);

        String url = "http://localhost:" + port + "/api/v1/user/update/" + email;

        //when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(requestBody)))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        assertThat(userOptional).isPresent(); // 이메일로 찾은 유저가 존재하는지 확인

        UserEntity updatedUser = userOptional.get(); // 유저 정보 가져오기
        // email, nickname, phone 일치 여부 확인
        assertThat(updatedUser.getNickname()).isEqualTo(newNickname);
        assertThat(updatedUser.getPhone()).isEqualTo(newPhone);
    }

    @Test
    @DisplayName("회원정보 password 수정 성공")
    public void update_pwd_비밀번호변경() throws Exception {

        //given
        String email = "testemail";
        String password = "testpwd";
        String nickname = "nktest";
        String phone = "999999";


        userService.join(email,nickname,password,phone);

        String newPwd = "newpwd";

        String url= "http://localhost:" + port + "/api/v1/user/update/password/"+email;
        UpdatePasswordDto dto=new UpdatePasswordDto(password,newPwd);

        // when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(dto)))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        assertThat(userOptional).isPresent(); // 이메일로 찾은 유저가 존재하는지 확인

        UserEntity updatedUser = userOptional.get(); // 유저 정보 가져오기
        // 변경한 newPwd 일치 여부 확인
        boolean isPasswordMatching = updatedUser.matchPassword(encoder, newPwd);
        assertThat(isPasswordMatching).isTrue();

    }

    @Test
    @DisplayName("회원 탈퇴 성공")
    public void withdraw_회원탈퇴() throws Exception {

        //given
        String email = "testemail";
        String password = "testpwd";
        String nickname = "nktest";
        String phone = "999999";

        userService.join(email,nickname,password,phone);


        String url = "http://localhost:" + port + "/api/v1/user/" + email;

        //when
        mvc.perform(delete(url).param("password",password))
                .andExpect(status().isOk())
                .andDo(print());

    }

}
