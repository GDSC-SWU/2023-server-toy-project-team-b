package com.gdsc_teamb.servertoyproject.domain.repository;

import com.gdsc_teamb.servertoyproject.domain.post.domain.HeartEntity;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import com.gdsc_teamb.servertoyproject.domain.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class HeartRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HeartRepository heartRepository;

    @Test
    @DisplayName("좋아요 저장 & 불러오기")
    public void saveLike_Load(){
        //Given
        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .email("abc@abc.com")
                .password("password1234")
                .nickname("nickname")
                .phone("01012345678")
                .build());

        PostEntity savedPost = postRepository.save(PostEntity.builder()
                .title("title-test")
                .content("content-test")
                .user(savedUser)
                .build());

        HeartEntity savedHeart = heartRepository.save(HeartEntity.builder()
                .user(savedUser)
                .post(savedPost)
                .build());

        // When
        List<HeartEntity> heartList= heartRepository.findAll();

        // Then
        HeartEntity heart=heartList.get(0);
        assertThat(heart.getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(heart.getPost().getId()).isEqualTo(savedPost.getId());


    }
}