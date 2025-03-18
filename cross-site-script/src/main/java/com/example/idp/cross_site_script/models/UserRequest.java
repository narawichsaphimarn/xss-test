package com.example.idp.cross_site_script.models;

import lombok.Data;

@Data
public class UserRequest {

  private Long id;
  private String email;
  private String username;
  private String password;
}
