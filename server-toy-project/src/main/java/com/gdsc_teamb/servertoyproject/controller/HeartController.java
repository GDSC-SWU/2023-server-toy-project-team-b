package com.gdsc_teamb.servertoyproject.controller;

import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardDto;
import com.gdsc_teamb.servertoyproject.dto.likeDto.HeartDto;
import com.gdsc_teamb.servertoyproject.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    // 좋아요 설정
    @PostMapping("/api/heart/{id}")
    public ResponseEntity<Object> insertHeart(@RequestBody HeartDto heartDto) throws Exception {
        return heartService.addHeart(heartDto);
    }

    // 좋아요 취소
    @DeleteMapping ("/api/heart/{id}")
    public ResponseEntity<Object> removeHeart(@PathVariable Long id) throws Exception {
        return heartService.deleteHeart(id);
    }
}
