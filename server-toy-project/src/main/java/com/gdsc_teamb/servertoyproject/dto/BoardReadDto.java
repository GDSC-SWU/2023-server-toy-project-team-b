package com.gdsc_teamb.servertoyproject.dto;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import lombok.Getter;

@Getter
public class BoardReadDto {

    private Long id;
    private String title;
    private String content;
    private UserEntity user;

    public BoardReadDto(PostEntity entity){
        this.id=entity.getId();
        this.content=entity.getContent();
        this.title=entity.getTitle();
        this.user=entity.getUser();
    }
}
