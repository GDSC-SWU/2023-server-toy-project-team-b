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

    // 게시글 작성
    // boardDto 는 작성할 게시글의 정보를 담음
    // 작성된 게시글의 id를 return
    @Transactional
    public Long save(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    // 게시글 수정
    // id는 수정할 게시글의 ID를 담고, boardUpdateDto 는 수정할 내용을 담음
    // 수정된 게시글의 ID return
    @Transactional
    public Long update(Long id, BoardUpdateDto boardUpdateDto){
        PostEntity post=boardRepository.findById(id).
                orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        post.update(boardUpdateDto.getTitle(), boardUpdateDto.getContent());

        return id;
    }

    // 게시글 세부 내용 조회
    // id는 조회할 게시글의 ID를 담음
    // 조회된 게시글의 세부 내용을 담은 BoardReadDto를 return
    @Transactional
    public BoardReadDto findById (Long id){
        // boardRepository 에서 주어진 id에 해당하는 게시글을 데이터베이스에서 조회
        PostEntity post=boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new BoardReadDto(post);
    }

    // 게시글 목록 조회
    // 게시글 목록을 담은 List<BoardListDto> return
    @Transactional (readOnly=true)
    public List<BoardListDto> findAllDesc(){
        return boardRepository.findAllDesc().stream()
                // BoardRepository 의 findAllDesc 메서드를 호출하여 게시글을 내림차순으로 조회 (쿼리 기능)
                .map(BoardListDto::new)// 각 게시글을 BoardListDto 로 변환
                .collect(Collectors.toList()); // 이후 리스트로 수집하여 반환
    }

    // 게시글 삭제
    // id는 삭제할 게시글의 ID
    @Transactional
    public void delete(Long id){
        // boardRepository 에서 주어진 id에 해당하는 게시글을 데이터베이스에서 조회
        PostEntity post=boardRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        // JpaRepository 에서 기본 제공되는 delete 메서드
        // 주어진 post 엔티티를 데이터베이스에서 삭제하는 역할
        boardRepository.delete(post);
    }


}
