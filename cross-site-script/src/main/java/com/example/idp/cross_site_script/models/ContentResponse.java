package com.example.idp.cross_site_script.models;

import lombok.Data;

@Data
public class ContentResponse {

  private Long id;
  private String title;
  private String content;
  private String user;
}