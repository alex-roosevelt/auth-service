package com.project.security.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequestDto {
  @JsonProperty("user_name")
  private String username;
  private String password;
}
