package com.Lvprasad.SecurityAppApplication.SecurityApp.repositories;

import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.Session;
import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByUser(UserEntity user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
