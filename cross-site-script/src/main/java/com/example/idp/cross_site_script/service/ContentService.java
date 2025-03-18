package com.example.idp.cross_site_script.service;

import com.example.idp.cross_site_script.entities.ContentEntity;
import com.example.idp.cross_site_script.entities.UserEntity;
import com.example.idp.cross_site_script.repositories.ContentRepository;
import com.example.idp.cross_site_script.repositories.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {

  private final ContentRepository contentRepository;

  private final UserRepository userRepository;

  public ContentEntity createContent(ContentEntity content, String username) throws Exception {
    UserEntity user = userRepository.findByUsername(username);
    if (Objects.isNull(user)) {
      throw new Exception("Invalid user");
    }
    content.setUserId(user.getId());
    return contentRepository.save(content);
  }

  public List<ContentEntity> getAllContents() {
    return contentRepository.findAll();
  }

  public Optional<ContentEntity> getContentById(Long id) {
    return contentRepository.findById(id);
  }

  public void deleteContent(Long id) {
    contentRepository.deleteById(id);
  }
}