package com.gdsc_teamb.servertoyproject.dto;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private UserEntity user;
    private String title;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @Builder
    public BoardDto(Long id, UserEntity user, String title,
                    String content, LocalDateTime created_at, LocalDateTime updated_at){
        this.id=id;
        this.user=user;
        this.title=title;
        this.content=content;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public PostEntity toEntity(){
        PostEntity build = PostEntity.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
        return build;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("게시판 제목은 공백일 수 없습니다.");
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("게시판 제목은 " + 50 + "자 이하로 입력해야 합니다.");
        }
        this.title = title;
    }

    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("게시판 내용은 공백일 수 없습니다.");
        }
        this.content = content;
    }

}
