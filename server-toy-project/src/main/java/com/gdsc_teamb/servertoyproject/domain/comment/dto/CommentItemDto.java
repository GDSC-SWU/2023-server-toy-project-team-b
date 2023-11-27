package com.gdsc_teamb.servertoyproject.domain.comment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentItemDto {
    private Long commentId;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;

    public CommentItemDto(Long commentId, @Size(min = 2, max = 8) @NotNull String nickname, @NotNull String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
    }
}
