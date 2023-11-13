package com.gdsc_teamb.servertoyproject.domain.comment.service;

import com.gdsc_teamb.servertoyproject.domain.comment.domain.CommentEntity;
import com.gdsc_teamb.servertoyproject.domain.comment.domain.CommentRepository;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.CommentItemDto;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.request.NewCommentReqDto;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.response.CommentResDto;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.response.ReadCommentResDto;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.response.UpdateCommentResDto;
import com.gdsc_teamb.servertoyproject.domain.comment.error.CommentErrorCode;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserRepository;
import com.gdsc_teamb.servertoyproject.global.Error.Exception.RestApiException;
import com.gdsc_teamb.servertoyproject.global.Error.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // temp
    private final UserRepository userRepository;

    // 댓글 작성
    @Transactional
    public CommentResDto addComment(UserEntity user, NewCommentReqDto newCommentReqDto) throws RestApiException {
        // temp
        if (user == null)
            user = userRepository.findById(1L).orElseThrow(() -> new RestApiException(GlobalErrorCode.INTERNAL_SERVER_ERROR));

        // post-id로 PostEntity 탐색
        PostEntity post = postRepository.findById(newCommentReqDto.getPost_id())
                .orElseThrow(() -> new RestApiException(CommentErrorCode.POST_NOT_FOUND));

        // CommentEntity 생성
        CommentEntity comment = CommentEntity.builder()
                .user(user)
                .post(post)
                .content(newCommentReqDto.getContent())
                .build();

        // Comment 저장
        Long commentId = commentRepository.save(comment).getId();

        // 결과 반환
        return CommentResDto.builder()
                .nickname(user.getNickname())
                .isPostWriter(post.getUser().equals(user))
                .postId(post.getId())
                .commentId(commentId)
                .content(comment.getContent())
                .build();
    }

    // 댓글 조회
    public ReadCommentResDto readComment(Long postId) throws RestApiException {
        // post-id 유효성 검사
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(CommentErrorCode.POST_NOT_FOUND));

        // 데이터 조회
        ArrayList<CommentItemDto> comments = commentRepository.findAllByPost(post);

        // 결과 반환
        return new ReadCommentResDto(postId, comments);
    }

    // 댓글 수정
    @Transactional
    public UpdateCommentResDto updateComment(UserEntity user, Long commentId, NewCommentReqDto reqDto) throws RestApiException {
        // temp
        if (user == null)
            user = userRepository.findById(1L).orElseThrow(() -> new RestApiException(GlobalErrorCode.INTERNAL_SERVER_ERROR));

        // comment-id 유효성 검사
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(CommentErrorCode.COMMENT_NOT_FOUND));

        // 댓글 작성자와 요청 사용자 일치 여부 확인
        if (!comment.getUser().equals(user))
            throw new RestApiException(CommentErrorCode.ACCESS_DENIED);

        // 데이터 수정
        comment.update(reqDto.getContent());

        // 결과 반환
        return UpdateCommentResDto.builder()
                .writerNickname(user.getNickname())
                .commentId(commentId)
                .content(comment.getContent())
                .build();
    }
}
