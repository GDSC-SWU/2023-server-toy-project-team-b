package com.gdsc_teamb.servertoyproject.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class NewCommentResDto {
    private String nickname;
    private Boolean isWriter;
    private Long postId;
    private Long commentId;
    private String content;
}
