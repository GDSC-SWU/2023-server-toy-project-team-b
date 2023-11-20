package com.gdsc_teamb.servertoyproject.dto.boardDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//게시글 수정 시 사용
//수정 할 게시글의 제목과 내용 담음
@Getter
@NoArgsConstructor
public class BoardUpdateDto {

    private String title;   // 수정할 게시글 제목
    private String content; // 수정할 게시글 내용


    //BoardUpdateDto 의 생성자
    @Builder
    public BoardUpdateDto(String title, String content){
        this.title=title;
        this.content=content;
    }
}
