package com.gdsc_teamb.servertoyproject.domain.repository;

import com.gdsc_teamb.servertoyproject.domain.post.domain.HeartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// LikeEntity 관리하는 JPA 레포
// LikeEntity 에 대한 CRUD 작업 제공
@Repository
public interface HeartRepository extends JpaRepository<HeartEntity, Long> {
}
