package com.example.idp.cross_site_script.repositories;

import com.example.idp.cross_site_script.entities.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<ContentEntity, Long> {

}