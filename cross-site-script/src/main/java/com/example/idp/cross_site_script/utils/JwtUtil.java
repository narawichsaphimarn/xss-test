package com.example.idp.cross_site_script.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${auth.secret}")
  private String secretKey;

  public boolean verifyToken(String token) {
    try {
      JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
      DecodedJWT jwt = verifier.verify(token.replace("Bearer ", ""));
      return jwt.getExpiresAt().after(new Date());
    } catch (JWTVerificationException e) {
      return false;
    }
  }

  public String getSubject(String token) {
    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
    DecodedJWT jwt = verifier.verify(token.replace("Bearer ", ""));
    return jwt.getSubject();
  }
}
