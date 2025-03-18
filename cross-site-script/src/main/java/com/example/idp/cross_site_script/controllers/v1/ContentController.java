package com.example.idp.cross_site_script.controllers.v1;

import com.example.idp.cross_site_script.entities.ContentEntity;
import com.example.idp.cross_site_script.entities.UserEntity;
import com.example.idp.cross_site_script.models.ContentResponse;
import com.example.idp.cross_site_script.repositories.UserRepository;
import com.example.idp.cross_site_script.service.ContentService;
import com.example.idp.cross_site_script.utils.JwtUtil;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/v1/api/contents")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ContentController {

  private final ContentService contentService;

  private final UserRepository userRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping
  public ResponseEntity<?> createContent(@RequestBody ContentEntity content,
      @RequestHeader("Authorization") String token) {
    if (!jwtUtil.verifyToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
    try {
      String username = jwtUtil.getSubject(token);
      return ResponseEntity.ok(contentService.createContent(content, username));
    } catch (Exception ex) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  public ResponseEntity<List<ContentEntity>> getAllContents() {
    return ResponseEntity.ok(contentService.getAllContents());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getContentById(@PathVariable Long id) {
    Optional<ContentEntity> content = contentService.getContentById(id);
    return content.map(data -> {
      ContentResponse contentResponse = new ContentResponse();
      BeanUtils.copyProperties(data, contentResponse);
      Optional<UserEntity> user = userRepository.findById(data.getUserId());
      user.ifPresent(userEntity -> contentResponse.setUser(userEntity.getUsername()));
      return new ResponseEntity<>(contentResponse, HttpStatus.OK);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteContent(@PathVariable Long id,
      @RequestHeader("Authorization") String token) {
    if (!jwtUtil.verifyToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
    contentService.deleteContent(id);
    return ResponseEntity.noContent().build();
  }
}