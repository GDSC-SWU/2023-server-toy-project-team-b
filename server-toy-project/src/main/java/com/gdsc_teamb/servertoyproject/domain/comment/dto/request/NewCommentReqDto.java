package com.gdsc_teamb.servertoyproject.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NewCommentReqDto {
    @NotNull
    private Long post_id;
    @NotBlank
    private String content;
}
