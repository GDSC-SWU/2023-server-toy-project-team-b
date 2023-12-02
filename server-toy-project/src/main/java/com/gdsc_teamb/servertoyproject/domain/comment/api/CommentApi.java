package com.gdsc_teamb.servertoyproject.domain.comment.api;

import com.gdsc_teamb.servertoyproject.domain.comment.dto.request.NewCommentReqDto;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.response.CommentResDto;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.response.ReadCommentResDto;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.response.UpdateCommentResDto;
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

    // 댓글 조회
    @GetMapping("/{post-id}")
    public ResponseEntity<ResponseDto> readComment(@PathVariable("post-id") Long postId) {
        ReadCommentResDto readCommentResDto = commentService.readComment(postId);

        return ResponseEntity.ok(DataResponseDto.of(readCommentResDto, 200));
    }

    // 댓글 수정
    @PatchMapping("/{comment-id}")
    public ResponseEntity<ResponseDto> updateComment(/*@AuthenticationPrincipal UserEntity user, */@RequestParam("temp") Long userId, @PathVariable("comment-id") Long commentId, @RequestBody @Valid NewCommentReqDto newCommentReqDto) {
        UpdateCommentResDto updateCommentResDto = commentService.updateComment(null, userId, commentId, newCommentReqDto);

        return ResponseEntity.status(201).body(DataResponseDto.of(updateCommentResDto, 201));
    }

    // 댓글 삭제
    @DeleteMapping("/{comment-id}")
    public ResponseEntity<ResponseDto> deleteComment(/*@AuthenticationPrincipal UserEntity user, */@RequestParam("temp") Long userId, @PathVariable("comment-id") Long commentId) {
        commentService.deleteComment(null, userId, commentId);

        return ResponseEntity.ok(ResponseDto.of(200));
    }
}
