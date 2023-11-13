package com.gdsc_teamb.servertoyproject.domain.comment.api;

import com.gdsc_teamb.servertoyproject.domain.comment.dto.request.NewCommentReqDto;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.response.CommentResDto;
import com.gdsc_teamb.servertoyproject.domain.comment.service.CommentService;
import com.gdsc_teamb.servertoyproject.global.common.DataResponseDto;
import com.gdsc_teamb.servertoyproject.global.common.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentApi {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{post-id}")
    public ResponseEntity<ResponseDto> addComment(/*@AuthenticationPrincipal UserEntity user*/@PathVariable("post-id") Long postId, @RequestBody @Valid NewCommentReqDto newCommentReqDto) {
        CommentResDto commentResDto = commentService.addComment(null, postId, newCommentReqDto);

        return ResponseEntity.status(201).body(DataResponseDto.of(commentResDto, 201));
    }
}
