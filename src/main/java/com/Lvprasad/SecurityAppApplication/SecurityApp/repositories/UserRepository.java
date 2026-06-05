package com.Lvprasad.SecurityAppApplication.SecurityApp.repositories;

import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmail(String email);
}
