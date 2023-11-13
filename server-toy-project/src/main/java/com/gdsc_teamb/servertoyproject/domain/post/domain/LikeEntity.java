package com.gdsc_teamb.servertoyproject.domain.post.domain;

import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Post_Like")
@NoArgsConstructor
@Getter
public class LikeEntity {
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

    public LikeEntity(UserEntity user, PostEntity post) {
        this.user = user;
        this.post = post;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof LikeEntity likeEntity)) return false;

        return Objects.equals(this.post, likeEntity.getPost()) &&
                Objects.equals(this.user, likeEntity.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, user);
    }
}
