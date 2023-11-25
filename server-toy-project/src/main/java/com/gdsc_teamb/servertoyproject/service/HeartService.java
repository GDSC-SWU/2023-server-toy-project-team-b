package com.gdsc_teamb.servertoyproject.service;

import com.gdsc_teamb.servertoyproject.domain.post.domain.HeartEntity;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.repository.HeartRepository;
import com.gdsc_teamb.servertoyproject.domain.repository.PostRepository;
import com.gdsc_teamb.servertoyproject.domain.repository.UserRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardDto;
import com.gdsc_teamb.servertoyproject.dto.likeDto.HeartDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.ExcludeSuperclassListeners;
import lombok.RequiredArgsConstructor;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseEntity<Object> addHeart(HeartDto heartDto) throws Exception {
        try {
            // heartDto 에서 User ID 조회
            UserEntity user=userRepository.findById(heartDto.getUserId().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User 없음"));
            // heartDto 에서 Post ID 조회
            PostEntity post=postRepository.findById(heartDto.getBoardId().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Post 없음"));
            // HeartEntity 생성
            HeartEntity heart=HeartEntity.builder()
                    .user(user)
                    .post(post)
                    .build();

            heartRepository.save(heart); // heart Entity 레포에 저장
            return ResponseEntity.ok("좋아요 등록 성공");

        } catch(Exception e){
            return ResponseEntity.badRequest().body("좋아요 등록 실패 " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<Object> deleteHeart(HeartDto heartDto) throws Exception {
        try{
            // User, Post id 존재하는지 검사
            UserEntity user=userRepository.findById(heartDto.getUserId().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User 없음"));

            PostEntity post=postRepository.findById(heartDto.getBoardId().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Post 없음"));

            // 해당하는 HeartEntity 가 실제로 존재하는지 확인
            HeartEntity heart=heartRepository.findByUserIdAndPostId(user,post)
                    .orElseThrow(() -> new IllegalArgumentException());

            // 좋아요 삭제
            heartRepository.delete(heart);
            return ResponseEntity.ok("좋아요 삭제 성공");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("좋아요 삭제 실패 " + e.getMessage());
        }

    }

}
