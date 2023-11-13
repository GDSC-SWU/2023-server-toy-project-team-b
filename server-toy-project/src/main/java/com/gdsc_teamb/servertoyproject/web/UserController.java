package com.gdsc_teamb.servertoyproject.web;


import com.gdsc_teamb.servertoyproject.service.UserService;
import com.gdsc_teamb.servertoyproject.web.dto.UpdatePasswordDto;
import com.gdsc_teamb.servertoyproject.web.dto.UserJoinRequestDto;
import com.gdsc_teamb.servertoyproject.web.dto.UserUpdateRequestDto;
import com.gdsc_teamb.servertoyproject.web.dto.UserWithdrawDto;
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
    public ResponseEntity<String> join(@RequestBody UserJoinRequestDto dto) throws Exception {
        userService.join(dto.getEmail(), dto.getNickname(), dto.getPassword(), dto.getPhone());
        return ResponseEntity.ok().body("회원가입 성공");
    };

    // 정보 수정 - phone, nickname
    @PutMapping("/api/v1/user/update/{email}")
    public ResponseEntity<String> update(@PathVariable String email, @RequestBody UserUpdateRequestDto dto) throws Exception {
        userService.update(email,dto.nickname(),dto.phone());
        return ResponseEntity.ok().body("수정 성공");
    }

    // 비밀번호 변경
    @PutMapping("/api/v1/user/update/password/{email}")
    public void updatePassword(@PathVariable String email,@RequestBody UpdatePasswordDto updatePasswordDto) throws Exception {
        userService.updatePassword(email,updatePasswordDto.checkPassword(),updatePasswordDto.toBePassword());
    }

    // 회원탈퇴
    @DeleteMapping("/api/v1/user/{email}")
    public void withdraw(@PathVariable String email,@RequestParam String password) throws Exception {
        userService.withdraw(email,password);
    }
}
