package com.gdsc_teamb.servertoyproject.dto;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

// 게시글의 생성 및 수정에 사용도는 데이터 전송
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id; // 게시글 ID
    private UserEntity user; // 작성자 정보
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private LocalDateTime created_at; // 생성 일자
    private LocalDateTime updated_at; // 수정 일자

    //BoardDto 생성자
    @Builder
    public BoardDto(Long id, UserEntity user, String title,
                    String content, LocalDateTime created_at, LocalDateTime updated_at){
        this.id=id;
        this.user=user;
        this.title=title;
        this.content=content;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // BoardDto 를 PostEntity 로 변환하는 메서드
    // 변환된 PostEntity 가 return
    public PostEntity toEntity(){
        PostEntity build = PostEntity.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
        return build;
    }

    // 게시글 제목 관련 설정 메서드
    // 제목이 공백이거나 50자 이상을 초과할 경우 IllegalArgumentException 발생됨
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("게시판 제목은 공백일 수 없습니다.");
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("게시판 제목은 " + 50 + "자 이하로 입력해야 합니다.");
        }
        this.title = title;
    }
    // 게시글 내용 관련 설정 메서드
    // 내용이 공백일 경우 IllegalArgumentException 발생됨
    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("게시판 내용은 공백일 수 없습니다.");
        }
        this.content = content;
    }

}
