package com.project.security.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseDto {
  private String jwtToken;
}
