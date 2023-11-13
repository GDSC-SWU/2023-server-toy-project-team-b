package com.gdsc_teamb.servertoyproject.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NewCommentReqDto {
    @NotBlank
    private String content;
}
