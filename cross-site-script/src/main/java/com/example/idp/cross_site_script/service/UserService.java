package com.example.idp.cross_site_script.service;

import com.example.idp.cross_site_script.entities.UserEntity;
import com.example.idp.cross_site_script.models.UserResponse;
import com.example.idp.cross_site_script.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public UserResponse createUser(String username, String password, String email) {
    if (userRepository.findByEmailAndUsername(email, username) != null) {
      throw new IllegalArgumentException("Username already exists");
    }

    UserEntity user = new UserEntity();
    user.setUsername(username);
    user.setPassword(password);
    user.setEmail(email);

    UserResponse userResponse = new UserResponse();
    BeanUtils.copyProperties(userRepository.save(user), userResponse);
    return userResponse;
  }
}