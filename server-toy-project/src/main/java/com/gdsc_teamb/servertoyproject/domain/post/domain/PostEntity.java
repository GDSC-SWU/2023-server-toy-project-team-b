package com.gdsc_teamb.servertoyproject.domain.post.domain;

import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Post")
@NoArgsConstructor
@Getter
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private UserEntity user;

    @Size(max = 50)
    @NotNull
    private String title;

    @NotNull
    private String content;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Builder
    public PostEntity(UserEntity user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PostEntity postEntity)) return false;
        if (this.id == null || postEntity.getId() == null) return false;

        return Objects.equals(this.id, postEntity.getId()) &&
                Objects.equals(this.user, postEntity.getUser()) &&
                Objects.equals(this.title, postEntity.getTitle()) &&
                Objects.equals(this.content, postEntity.getContent()) &&
                Objects.equals(this.created_at, postEntity.getCreated_at());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, created_at);
    }
}
