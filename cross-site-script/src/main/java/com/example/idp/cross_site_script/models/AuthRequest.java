package com.example.idp.cross_site_script.models;

import lombok.Data;

@Data
public class AuthRequest {
  private String username;
  private String password;
}
