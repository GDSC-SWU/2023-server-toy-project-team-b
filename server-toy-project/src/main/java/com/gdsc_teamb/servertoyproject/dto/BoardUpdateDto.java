package com.gdsc_teamb.servertoyproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateDto {

    private String title;
    private String content;

    @Builder
    public BoardUpdateDto(String title, String content){
        this.title=title;
        this.content=content;
    }
}
