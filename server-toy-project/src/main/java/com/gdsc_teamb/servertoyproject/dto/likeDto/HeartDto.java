package com.gdsc_teamb.servertoyproject.dto.likeDto;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class HeartDto {
    private UserEntity userId; // 좋아요 한 유저의 ID
    private PostEntity boardId; // 좋아요 한 게시물의 ID

    //BoardDto 생성자
    @Builder
    public HeartDto(UserEntity userId, PostEntity boardId){
        this.userId=userId;
        this.boardId=boardId;
    }
}
