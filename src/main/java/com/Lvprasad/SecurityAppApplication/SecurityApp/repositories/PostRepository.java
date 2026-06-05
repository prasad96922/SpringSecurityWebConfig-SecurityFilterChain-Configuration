package com.Lvprasad.SecurityAppApplication.SecurityApp.repositories;


import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository  extends JpaRepository<PostEntity, Long> {

}
