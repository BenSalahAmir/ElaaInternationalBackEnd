package com.bezkoder.spring.security.mongodb.payload.response;

import java.util.List;

public class UserInfoResponse {
  private String id;
  private String username;
  private String email;
  private String jwtCookie;
  private List<String> roles;

  public UserInfoResponse(String id, String username, String email, List<String> roles, String jwtCookie) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
    this.jwtCookie=jwtCookie;
  }

  public String getId() {
    return id;
  }

  public String getJwtCookie() {
    return jwtCookie;
  }

  public void setJwtCookie(String jwtCookie) {
    this.jwtCookie = jwtCookie;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }
}
