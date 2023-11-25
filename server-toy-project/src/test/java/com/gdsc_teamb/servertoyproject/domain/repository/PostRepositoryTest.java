package com.gdsc_teamb.servertoyproject.domain.repository;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("게시글 저장 & 불러오기")
    public void savePost_Load(){
        final String TITLE = "title-test";
        final String CONTENT = "content-test";
        //given
        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .email("abc@abc.com")
                .password("password1234")
                .nickname("nickname")
                .phone("01012345678")
                .build());

        PostEntity savedPost = postRepository.save(PostEntity.builder()
                .title(TITLE)
                .content(CONTENT)
                .user(savedUser)
                .build());

        //when
        List<PostEntity> postsList= postRepository.findAll();

        //then
        PostEntity posts=postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(posts.getContent()).isEqualTo(savedPost.getContent());
        assertThat(posts.getUser().getId()).isEqualTo(savedPost.getUser().getId());

    }


}