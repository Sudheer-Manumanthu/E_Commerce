package com.sudheer.userauthenticationservice.repositories;

import com.sudheer.userauthenticationservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
//
//    @Query("select S from Session s where s.token = :token and s.user_id = :userId")
//    Optional<Session> findByTokenAndUser_Id(String token, Long userId);

    @Query("select s from Session s where s.token = :token and s.user.id = :userId")
    Optional<Session> findByTokenAndUser_Id(@Param("token") String token, @Param("userId") Long userId);


}
