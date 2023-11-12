package com.gdsc_teamb.servertoyproject.dto;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class BoardListDto {

    private Long id;
    private String title;
    private UserEntity user;
    private LocalDateTime updated_at;

    public BoardListDto(PostEntity entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.user=entity.getUser();
        this.updated_at=entity.getUpdated_at();
    }
}
