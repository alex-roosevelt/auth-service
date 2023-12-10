package com.project.eneler.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResponseDto {
  private String message;
  private String code;
}
