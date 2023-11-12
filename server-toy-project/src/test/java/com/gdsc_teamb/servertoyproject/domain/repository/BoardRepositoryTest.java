package com.gdsc_teamb.servertoyproject.domain.repository;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.repository.BoardRepository;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.service.BoardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanup(){
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }

    final String TITLE = "title-test";
    final String CONTENT = "content-test";

    @Test
    public void 게시글저장_불러오기(){
        //given
        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .email("abc@abc.com")
                .password("password1234")
                .nickname("nickname")
                .phone("01012345678")
                .build());

        PostEntity savedPost = boardRepository.save(PostEntity.builder()
                .title(TITLE)
                .content(CONTENT)
                .user(savedUser)
                .build());

        //when
        List<PostEntity> postsList=boardRepository.findAll();

        //then
        PostEntity posts=postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(posts.getContent()).isEqualTo(savedPost.getContent());
        assertThat(posts.getUser().getId()).isEqualTo(savedPost.getUser().getId());

    }
}