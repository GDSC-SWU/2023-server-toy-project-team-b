package com.gdsc_teamb.servertoyproject.domain.repository;

import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
