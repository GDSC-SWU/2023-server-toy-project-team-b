package com.gdsc_teamb.servertoyproject.login;

import com.gdsc_teamb.servertoyproject.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public JwtToken login(@RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        JwtToken jwtToken = memberService.login(email, password);
        return jwtToken;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
