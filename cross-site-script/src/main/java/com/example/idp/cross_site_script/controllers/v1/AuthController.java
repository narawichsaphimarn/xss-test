package com.example.idp.cross_site_script.controllers.v1;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.idp.cross_site_script.entities.UserEntity;
import com.example.idp.cross_site_script.models.AuthRequest;
import com.example.idp.cross_site_script.repositories.UserRepository;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
@CrossOrigin("*")
public class AuthController {

  @Value("${auth.secret}")
  private String secretKey;

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/login")
  public Map<String, String> login(@RequestBody AuthRequest request) {
    UserEntity entity = userRepository.findByUsernameAndPassword(request.getUsername(),
        request.getPassword());
    if (Objects.isNull(entity)) {
      throw new RuntimeException("Invalid username or password");
    }

    String token = JWT.create().withSubject(request.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_00))
        .sign(Algorithm.HMAC256(secretKey));

    Map<String, String> response = new HashMap<>();
    response.put("token", token);
    response.put("username", entity.getUsername());
    return response;
  }
}