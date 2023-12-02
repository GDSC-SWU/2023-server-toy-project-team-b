package com.gdsc_teamb.servertoyproject.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UpdateCommentResDto {
    private String writerNickname;
    private Long commentId;
    private String content;
}
