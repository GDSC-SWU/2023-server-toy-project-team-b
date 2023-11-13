package com.gdsc_teamb.servertoyproject.domain.comment.service;

import com.gdsc_teamb.servertoyproject.domain.comment.domain.CommentEntity;
import com.gdsc_teamb.servertoyproject.domain.comment.domain.CommentRepository;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.request.NewCommentReqDto;
import com.gdsc_teamb.servertoyproject.domain.comment.dto.response.NewCommentResDto;
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

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // temp
    private final UserRepository userRepository;

    // 댓글 작성
    @Transactional
    public NewCommentResDto addComment(UserEntity user, NewCommentReqDto newCommentReqDto) throws RestApiException {
        // temp
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
        return NewCommentResDto.builder()
                .nickname(user.getNickname())
                .isWriter(post.getUser().equals(user))
                .postId(post.getId())
                .commentId(commentId)
                .content(comment.getContent())
                .build();
    }
}
