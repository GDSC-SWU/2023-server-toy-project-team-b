package com.gdsc_teamb.servertoyproject.domain.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "User")
@NoArgsConstructor
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @Size(min = 2, max = 8)
    @NotNull
    private String nickname;

    @Size(max = 11)
    @NotNull
    private String phone;

    @CreationTimestamp
    private LocalDateTime created_at;

    @Builder
    public UserEntity(String email, String password, String nickname, String phone) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserEntity userEntity)) return false;

        return Objects.equals(this.email, userEntity.getEmail()) &&
                Objects.equals(this.nickname, userEntity.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, nickname);
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }
    public void updatePhone(String phone){
        this.phone = phone;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    //비밀번호 변경, 회원 탈퇴 시, 비밀번호를 확인하며, 이때 비밀번호의 일치여부를 판단하는 메서드
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword, getPassword());
    }
}
