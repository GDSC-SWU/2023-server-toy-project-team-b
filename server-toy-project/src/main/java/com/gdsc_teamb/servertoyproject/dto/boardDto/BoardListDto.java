package com.gdsc_teamb.servertoyproject.dto.boardDto;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;

// 게시글 목록 조회 시 사용
@Getter
public class BoardListDto {

    private Long id; // 게시글 ID
    private String title; // 게시글 제목
    private String nickname; // 작성자 닉네임
    private LocalDateTime updated_at; // 수정 일자

    // BoardListDto 의 생성자
    public BoardListDto(PostEntity entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.nickname=entity.getUser().getNickname(); // UserEntity 에서 nickname 가져오기
        this.updated_at=entity.getUpdated_at();
    }
}
