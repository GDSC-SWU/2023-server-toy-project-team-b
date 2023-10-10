package com.gdsc_teamb.servertoyproject.domain.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    @Min(2)
    @Max(8)
    @NotNull
    private String nickname;

    @Max(11)
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
}
