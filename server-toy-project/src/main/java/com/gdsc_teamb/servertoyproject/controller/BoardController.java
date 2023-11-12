package com.gdsc_teamb.servertoyproject.controller;

import com.gdsc_teamb.servertoyproject.dto.BoardDto;
import com.gdsc_teamb.servertoyproject.dto.BoardListDto;
import com.gdsc_teamb.servertoyproject.dto.BoardReadDto;
import com.gdsc_teamb.servertoyproject.dto.BoardUpdateDto;
import com.gdsc_teamb.servertoyproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    //생성
    @PostMapping("/api/boards")
    public Long save(@RequestBody BoardDto boardDto){
        return boardService.save(boardDto);
    }

    //수정
    @PutMapping("/api/boards/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateDto requestDto){
        return boardService.update(id, requestDto);
    }

    //읽기 (조회)
    @GetMapping("/api/boards/{id}")
    public BoardReadDto findById (@PathVariable Long id){
        return boardService.findById(id);
    }

    // 목록읽기
    @GetMapping("/api/boards")
    public List<BoardListDto> findAllDesc() {
        return boardService.findAllDesc();
    }

    //삭제
    @DeleteMapping("/api/boards/{id}")
    public Long delete(@PathVariable Long id){
        boardService.delete(id);
        return id;
    }
}
