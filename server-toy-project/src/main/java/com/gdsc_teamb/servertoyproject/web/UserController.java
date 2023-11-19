package com.gdsc_teamb.servertoyproject.web;

import com.gdsc_teamb.servertoyproject.domain.response.domain.CommonResponse;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.service.ApiUtils;
import com.gdsc_teamb.servertoyproject.service.UserService;
import com.gdsc_teamb.servertoyproject.web.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String save() throws Exception {
        return "hello";
    };

    // 회원가입
    @PostMapping("/api/v1/user/join")
    public CommonResponse<String> join(@RequestBody UserJoinRequestDto dto) throws Exception {
        userService.join(dto.getEmail(), dto.getNickname(), dto.getPassword(), dto.getPhone());
        //return ResponseEntity.ok().body("회원가입 성공");
        return ApiUtils.success(200, dto.getEmail());
    };

    // 정보 수정 - phone, nickname
    @PutMapping("/api/v1/user/update/{email}")
    public CommonResponse<String> update(@PathVariable String email, @RequestBody UserUpdateRequestDto dto) throws Exception {
        userService.update(email,dto.nickname(),dto.phone());
        return ApiUtils.success(200,null);
    }

    // 비밀번호 변경
    @PutMapping("/api/v1/user/update/password/{email}")
    public void updatePassword(@PathVariable String email,@RequestBody UpdatePasswordDto updatePasswordDto) throws Exception {
        userService.updatePassword(email,updatePasswordDto.checkPassword(),updatePasswordDto.toBePassword());
    }

    // 회원탈퇴
    @DeleteMapping("/api/v1/user/{email}")
    public void withdraw(@PathVariable String email,@RequestParam(value="password",required = true) String password) throws Exception {
        userService.withdraw(email,password);
    }
}
