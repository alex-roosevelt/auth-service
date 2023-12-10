package com.project.security.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class UserRequestDto {
  @JsonProperty("full_name")
  private String fullName;
  @JsonProperty("user_name")
  private String userName;
  private String email;
  private String password;
  private List<String> roles;
}
