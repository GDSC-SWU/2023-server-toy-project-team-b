package com.gdsc_teamb.servertoyproject.service;

import com.gdsc_teamb.servertoyproject.domain.post.domain.HeartEntity;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.repository.HeartRepository;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardDto;
import com.gdsc_teamb.servertoyproject.dto.likeDto.HeartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;

    //@Transactional
    //public Long addHeart(HeartDto heartDto) {
        //return heartRepository.save();
   // }

    @Transactional
    public void deleteHeart(Long id){
        HeartEntity heart= heartRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        // JpaRepository 에서 기본 제공되는 delete 메서드
        // 주어진 post 엔티티를 데이터베이스에서 삭제하는 역할
        heartRepository.delete(heart);
    }

}
