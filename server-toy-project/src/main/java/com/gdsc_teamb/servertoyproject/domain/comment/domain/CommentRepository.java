package com.gdsc_teamb.servertoyproject.domain.comment.domain;

import com.gdsc_teamb.servertoyproject.domain.comment.dto.CommentItemDto;
import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("select new com.gdsc_teamb.servertoyproject.domain.comment.dto.CommentItemDto(c.id, u.nickname, c.content, c.created_at) " +
            "from CommentEntity c " +
            "left join UserEntity u on c.user = u " +
            "where c.post = :post")
    ArrayList<CommentItemDto> findAllByPost(PostEntity post);
}
