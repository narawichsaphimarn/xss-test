package com.example.idp.cross_site_script.controllers.v1;

import com.example.idp.cross_site_script.models.UserRequest;
import com.example.idp.cross_site_script.models.UserResponse;
import com.example.idp.cross_site_script.repositories.UserRepository;
import com.example.idp.cross_site_script.service.UserService;
import com.example.idp.cross_site_script.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/users")
@CrossOrigin("*")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id,
      @RequestHeader("Authorization") String token) {
    if (!jwtUtil.verifyToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
    return ResponseEntity.ok(
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String token) {
    if (!jwtUtil.verifyToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
    return ResponseEntity.ok(userRepository.findAll());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUserById(@PathVariable Long id,
      @RequestHeader("Authorization") String token) {
    if (!jwtUtil.verifyToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
    userRepository.deleteById(id);
    return ResponseEntity.ok("User deleted");
  }

  @PostMapping
  public ResponseEntity<?> createUser(@RequestBody UserRequest user) {
    UserResponse resp = userService.createUser(user.getUsername(), user.getPassword(),
        user.getEmail());
    return new ResponseEntity<>(resp, HttpStatus.OK);
  }
}