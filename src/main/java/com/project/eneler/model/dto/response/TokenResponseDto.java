package com.project.eneler.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseDto {
  private String jwtToken;
}
