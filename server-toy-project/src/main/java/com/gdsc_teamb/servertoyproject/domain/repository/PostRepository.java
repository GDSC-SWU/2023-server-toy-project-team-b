package com.gdsc_teamb.servertoyproject.domain.repository;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// PostEntity 관리하는 JPA 레포
// PostEntity 에 대한 CRUD 작업 제공
@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    //ID 기준으로 내림차순되게 쿼리 정의함
    //@Query("SELECT p FROM PostEntity p ORDER BY p.id DESC")
    List<PostEntity> findAllByOrderByIdDesc();
}
