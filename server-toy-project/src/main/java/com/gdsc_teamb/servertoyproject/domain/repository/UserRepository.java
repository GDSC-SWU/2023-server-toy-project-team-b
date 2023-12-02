//package com.gdsc_teamb.servertoyproject.domain.repository;
//
//import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//
//// UserEntity 관리하는 JPA 레포
//// UserEntity 에 대한 CRUD 작업 제공
//@Repository
//public interface UserRepository extends JpaRepository<UserEntity, Long> {
//    // nickname 으로 user 검색해서 찾아오기
//    Optional<UserEntity> findByNickname(String nickname);
//}
// 민지언니가 user 레포 만들어놔서 그걸로 경로 다 바꾸고 제꺼는 주석처리 했습니다~!