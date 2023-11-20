package com.gdsc_teamb.servertoyproject.web;

import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.exception.AppException;
import com.gdsc_teamb.servertoyproject.exception.ErrorCode;
import com.gdsc_teamb.servertoyproject.response.ApiResponse;
import com.gdsc_teamb.servertoyproject.service.UserService;
import com.gdsc_teamb.servertoyproject.web.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


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
    public ApiResponse<?> join(@RequestBody UserJoinRequestDto dto) throws Exception {
        try {
            UserResponseDto result = userService.join(dto.getEmail(), dto.getNickname(), dto.getPassword(), dto.getPhone());
            return ApiResponse.createSuccess(result);
        } catch (AppException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return ApiResponse.createError("Error occurred: " + errorCode.getMessage());
        }
    };

    // 정보 수정 - phone, nickname
    @PutMapping("/api/v1/user/update/{email}")
    public ApiResponse<?> update(@PathVariable String email, @RequestBody UserUpdateRequestDto dto) throws Exception {
        try {
            UserResponseDto result = userService.update(email, dto.nickname(), dto.phone());
            return ApiResponse.createSuccess(result);
        } catch (AppException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return ApiResponse.createError("Error occurred: " + errorCode.getMessage());
        }

    }

    // 비밀번호 변경
    @PutMapping("/api/v1/user/update/password/{email}")
    public ApiResponse<?>  updatePassword(@PathVariable String email,@RequestBody UpdatePasswordDto updatePasswordDto) throws Exception {

        try {
            userService.updatePassword(email,updatePasswordDto.checkPassword(),updatePasswordDto.toBePassword());
            return ApiResponse.createSuccessWithNoContent();
        } catch (AppException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return ApiResponse.createError("Error occurred: " + errorCode.getMessage());
        }

    }

    // 회원탈퇴
    @DeleteMapping("/api/v1/user/{email}")
    public ApiResponse<?> withdraw(@PathVariable String email, @RequestParam(value="password",required = true) String password) throws Exception {

        try {
            userService.withdraw(email,password);
            return ApiResponse.createSuccessWithNoContent();
        } catch (AppException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return ApiResponse.createError("Error occurred: " + errorCode.getMessage());
        }
    }
}
