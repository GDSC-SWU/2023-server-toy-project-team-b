package com.gdsc_teamb.servertoyproject.domain.comment.dto.response;

import com.gdsc_teamb.servertoyproject.domain.comment.dto.CommentItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class ReadCommentResDto {
    private Long postId;
    private ArrayList<CommentItemDto> comments;
}
