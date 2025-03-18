package com.example.idp.cross_site_script.repositories;

import com.example.idp.cross_site_script.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findByUsernameAndPassword(String username, String password);

  UserEntity findByEmailAndUsername(String email, String username);

  UserEntity findByUsername(String username);
}