package com.gdsc_teamb.servertoyproject.domain.repository;

import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// UserEntity 관리하는 JPA 레포
// UserEntity에 대한 CRUD 작업 제공
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
