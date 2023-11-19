package com.gdsc_teamb.servertoyproject.dto;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import lombok.Getter;

// 게시글 조회 시 사용
@Getter
public class BoardReadDto {

    private Long id; // 게시글 ID
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String user; // 작성자 정보

    // BoardReadDto 의 생성자
    public BoardReadDto(PostEntity entity){
        this.id=entity.getId();
        this.content=entity.getContent();
        this.title=entity.getTitle();
        this.user=entity.getUser().getNickname();
    }
}
