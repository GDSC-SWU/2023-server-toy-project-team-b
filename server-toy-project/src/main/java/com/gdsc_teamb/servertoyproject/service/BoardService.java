package com.gdsc_teamb.servertoyproject.service;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.repository.PostRepository;
import com.gdsc_teamb.servertoyproject.domain.repository.UserRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardDto;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardListDto;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardReadDto;
import com.gdsc_teamb.servertoyproject.dto.boardDto.BoardUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BoardService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 작성
    // boardDto 는 작성할 게시글의 정보를 담음
    // 작성된 게시글의 id를 return
    @Transactional
    public ResponseEntity<Object> savePost(BoardDto boardDto) {
        try{
            UserEntity user=userRepository.findByNickname(boardDto.getNickname()).
                    orElseThrow(()->new IllegalArgumentException());
            // PostEntity 생성
            PostEntity postEntity = boardDto.toEntity(user);

            postRepository.save(postEntity); // 저장

            return ResponseEntity.ok(boardDto.toEntity(user).getId());
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
/*
    // 게시글 수정
    // id는 수정할 게시글의 ID를 담고, boardUpdateDto 는 수정할 내용을 담음
    // 수정된 게시글의 ID return
    @Transactional
    public Long update(Long id, BoardUpdateDto boardUpdateDto){
        PostEntity post= postRepository.findById(id).
                orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        post.update(boardUpdateDto.getTitle(), boardUpdateDto.getContent());

        return id;
    }*/
    @Transactional
    public ResponseEntity<Object> updatePost(Long id, BoardUpdateDto boardUpdateDto) {
        try {
            PostEntity post = postRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException());

            post.update(boardUpdateDto.getTitle(), boardUpdateDto.getContent());

            return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다. id= "+id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("게시글 수정에 실패했습니다. " + e.getMessage());
        }
    }

/*
    // 게시글 세부 내용 조회
    // id는 조회할 게시글의 ID를 담음
    // 조회된 게시글의 세부 내용을 담은 BoardReadDto를 return
    @Transactional
    public BoardReadDto findById (Long id){
        // boardRepository 에서 주어진 id에 해당하는 게시글을 데이터베이스에서 조회
        PostEntity post= postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new BoardReadDto(post);
    }
*/
    @Transactional
    public ResponseEntity<Object> findById (Long id) {
        try {
            // boardRepository 에서 주어진 id에 해당하는 게시글을 데이터베이스에서 조회
            PostEntity post = postRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException());
            return ResponseEntity.ok("게시글이 성공적으로 조회되었습니다."+ new BoardReadDto(post));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("게시글 조회에 실패했습니다. " + e.getMessage());
        }

    }
    // 게시글 목록 조회
    // 게시글 목록을 담은 List<BoardListDto> return
    @Transactional (readOnly=true)
    public List<BoardListDto> findAllByOrderByIdDesc(){
        return postRepository.findAllByOrderByIdDesc().stream()
                // BoardRepository 의 findAllDesc 메서드를 호출하여 게시글을 내림차순으로 조회 (쿼리 기능)
                .map(BoardListDto::new)// 각 게시글을 BoardListDto 로 변환
                .collect(Collectors.toList()); // 이후 리스트로 수집하여 반환
    }

    // 게시글 삭제
    // id는 삭제할 게시글의 ID
    @Transactional
    public ResponseEntity<Object> deletePost(Long id){
        try{
            // boardRepository 에서 주어진 id에 해당하는 게시글을 데이터베이스에서 조회
            PostEntity post= postRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
            // JpaRepository 에서 기본 제공되는 delete 메서드
            // 주어진 post 엔티티를 데이터베이스에서 삭제하는 역할
            postRepository.delete(post);
            return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("게시글 삭제에 실패했습니다. " + e.getMessage());
        }

    }


}
