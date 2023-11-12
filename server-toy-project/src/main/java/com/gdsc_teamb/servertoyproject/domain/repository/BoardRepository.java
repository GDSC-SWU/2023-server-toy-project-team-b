package com.gdsc_teamb.servertoyproject.domain.repository;

import com.gdsc_teamb.servertoyproject.domain.post.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BoardRepository extends JpaRepository<PostEntity, Long> {
    @Query("SELECT p FROM PostEntity p ORDER BY p.id DESC")
    List<PostEntity> findAllDesc();
}
