package com.gdsc_teamb.servertoyproject.service;

import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserRepository;
import com.gdsc_teamb.servertoyproject.exception.AppException;
import com.gdsc_teamb.servertoyproject.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public String join(String email,String nickname,String password,String phone){

        // nickname 중복 체크
        userRepository.findByNickname(nickname)
                .ifPresent(userEntity -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATION,nickname);
                });

        // email 중복 체크
        userRepository.findByEmail(email)
                .ifPresent(userEntity -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATION,email);
                });

        // 저장
        UserEntity user=UserEntity.builder()
                .email(email)
                .password(encoder.encode(password))
                .nickname(nickname)
                .phone(phone)
                .build();

        userRepository.save(user);

        return "SUCCESS";
    }


    // email, nickname 변경
    @Transactional
    public String update(String email, Optional<String> nickname, Optional<String> phone) throws Exception {

        UserEntity user= userRepository.findByEmail(email).orElseThrow(() ->
                new AppException(ErrorCode.NOTFOUND_USER,email));


        nickname.ifPresent(user::updateNickname);
        phone.ifPresent(user::updatePhone);

        return "SUCCESS";
    }


    // 비밀번호 변경
    public void updatePassword(String email,String checkPassword, String toBePassword) throws Exception {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        if(!user.matchPassword(encoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        user.updatePassword(encoder, toBePassword);
    }

    // 회원 탈퇴
    public void withdraw(String email,String checkPassword) throws Exception {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        if(!user.matchPassword(encoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }


}
