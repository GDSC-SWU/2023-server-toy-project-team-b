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
            // User, Post id 로 해당 user, post 존재하는지 검사
            UserEntity user=userRepository.findById(heartDto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 id로 검색되는 User 없음"));
            PostEntity post=postRepository.findById(heartDto.getPostId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 id로 검색되는 Post 없음"));

            // HeartEntity 생성
            HeartEntity heart=HeartEntity.builder()
                    .user(user)
                    .post(post)
                    .build();

            heartRepository.save(heart); // heart Entity 레포에 저장

            return ResponseEntity.ok("좋아요 등록 성공, 닉네임: "+user.getNickname());

        } catch(Exception e){
            return ResponseEntity.badRequest().body("좋아요 등록 실패 " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<Object> deleteHeart(Long id) throws Exception {
        try{
            // User, Post id 로 해당 user, post 존재하는지 검사
            PostEntity post=postRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Post 없음"));

            UserEntity user=userRepository.findById(post.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User 없음"));


            // id 통해서 해당하는 Heart 가 실제로 존재하는지 확인
            HeartEntity heart=heartRepository.findByUserIdAndPostId(user.getId(),post.getId())
                    .orElseThrow(() -> new IllegalArgumentException());

            // 좋아요 삭제
            heartRepository.delete(heart);
            return ResponseEntity.ok("좋아요 삭제 성공, 닉네임: "+user.getNickname());
        }catch(Exception e){
            return ResponseEntity.badRequest().body("좋아요 삭제 실패 " + e.getMessage());
        }

    }

}
