package com.gdsc_teamb.servertoyproject.domain.post.domain;

import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Post_Like")
@NoArgsConstructor
@Getter
public class HeartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private UserEntity user;

    @ManyToOne
    @NotNull
    private PostEntity post;

    @CreationTimestamp
    private LocalDateTime created_at;

    @Builder
    public HeartEntity(UserEntity user, PostEntity post) {
        this.user = user;
        this.post = post;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof HeartEntity heartEntity)) return false;

        return Objects.equals(this.post, heartEntity.getPost()) &&
                Objects.equals(this.user, heartEntity.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, user);
    }
}
