package com.gdsc_teamb.servertoyproject.service;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.repository.BoardRepository;
import com.gdsc_teamb.servertoyproject.dto.BoardDto;
import com.gdsc_teamb.servertoyproject.dto.BoardListDto;
import com.gdsc_teamb.servertoyproject.dto.BoardReadDto;
import com.gdsc_teamb.servertoyproject.dto.BoardUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BoardService {
    private final BoardRepository boardRepository;

    //게시글 작성
    @Transactional
    public Long save(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    //게시글 수정
    @Transactional
    public Long update(Long id, BoardUpdateDto boardUpdateDto){
        PostEntity post=boardRepository.findById(id).
                orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        post.update(boardUpdateDto.getTitle(), boardUpdateDto.getContent());

        return id;
    }

    //게시글 세부 내용 조회
    //특정 게시글의 세부 내용을 조회하는 기능
    @Transactional
    public BoardReadDto findById (Long id){
        PostEntity entity=boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new BoardReadDto(entity);
    }

    //게시글 목록 조회
    @Transactional (readOnly=true)
    public List<BoardListDto> findAllDesc(){
        return boardRepository.findAllDesc().stream()
                .map(BoardListDto::new)
                .collect(Collectors.toList());
    }

    //게시글 삭제
    @Transactional
    public void delete(Long id){
        PostEntity posts=boardRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        boardRepository.delete(posts);
    }


}
