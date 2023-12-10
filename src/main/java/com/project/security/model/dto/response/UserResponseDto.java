package com.project.security.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
  private String id;
  @JsonProperty("full_name")
  private String fullName;
  @JsonProperty("user_name")
  private String userName;
  private String email;
  private List<String> roles;
}
