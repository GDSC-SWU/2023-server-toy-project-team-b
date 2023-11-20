package com.gdsc_teamb.servertoyproject.controller;

import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardDto;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardListDto;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardReadDto;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardUpdateDto;
import com.gdsc_teamb.servertoyproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class BoardController {
    private final BoardService boardService;

    // HTTP POST 요청을 통해 새로운 게시글을 생성
    // 매개변수: 새로운 게시글을 생성하기 위한 데이터를 담은 DTO
    // @RequestBody 어노테이션은 JSON 데이터를 BoardDto 객체로 변환
    // 반환 값: 새로 생성된 게시글의 ID를 반환함
    // 반환된 ID는 클라이언트에게 제공 (응답으로 전송) -> 클라이언트가 나중에 수정, 삭제할때 사용
    @PostMapping("/api/boards")
    public Long save(@RequestBody BoardDto boardDto){
        return boardService.save(boardDto);
    }

    // HTTP PUT 요청을 통해 특정 ID의 게시글을 수정
    // 매개변수: 수정할 게시글의 ID, 수정할 내용이 담긴 DTO
    // @PathVariable 을 통해 경로에서 추출된 게시글의 ID를 전달 받고, @RequestBody를 통해 클라이언트로부터 전달된 JSON 데이터를 변환하여 requestDto로 전달
    // 반환 값: 수정이 완료된 게시글의 ID를 반환
    @PutMapping("/api/boards/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateDto requestDto){
        return boardService.update(id, requestDto);
    }

    // HTTP GET 요청을 통해 특정 ID의 게시글을 조회
    // 매개변수: 조회할 게시글의 ID를 전달받음
    // @PathVariable을 통해 경로에서 추출된 게시글의 ID를 전달 받음
    // 반환 값: 조회된 게시글의 세부 정보가 담긴 DTO를 반환
    @GetMapping("/api/boards/{id}")
    public BoardReadDto findById (@PathVariable Long id){
        return boardService.findById(id);
    }

    // HTTP GET 요청을 통해 모든 게시글 목록을 조회
    // 반환 값: 조회된 모든 게시글의 목록이 담긴 DTO 들을 리스트로 반환
    @GetMapping("/api/boards")
    public List<BoardListDto> findAllDesc() {
        return boardService.findAllByOrderByIdDesc();
    }

    // HTTP DELETE 요청을 통해 특정 ID의 게시글을 삭제
    // 매개변수: 삭제할 게시글의 ID를 전달 받음
    // @PathVariable 을 통해 경로에서 추출된 게시글의 ID를 전달 받음
    // 반환 값: 삭제가 완료된 게시글의 ID를 반환
    @DeleteMapping("/api/boards/{id}")
    public Long delete(@PathVariable Long id){
        boardService.delete(id);
        return id;
    }
}
