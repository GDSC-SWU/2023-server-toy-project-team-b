package com.gdsc_teamb.servertoyproject.domain.comment.domain;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Comment")
@NoArgsConstructor
@Getter
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private UserEntity user;

    @ManyToOne
    @NotNull
    private PostEntity post;

    @NotNull
    private String content;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Builder
    public CommentEntity(UserEntity user, PostEntity post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CommentEntity commentEntity)) return false;
        if (this.id == null || commentEntity.getId() == null) return false;

        return Objects.equals(this.id, commentEntity.getId()) &&
                Objects.equals(this.user, commentEntity.getUser()) &&
                Objects.equals(this.post, commentEntity.getPost()) &&
                Objects.equals(this.content, commentEntity.getContent()) &&
                Objects.equals(this.created_at, commentEntity.getCreated_at());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, post, created_at);
    }

    @Transactional
    public void update(String content) {
        this.content = content;
    }
}
