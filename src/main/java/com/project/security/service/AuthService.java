package com.project.security.service;

import com.project.security.model.dto.request.LoginRequestDto;
import com.project.security.model.dto.request.SignUpRequestDto;
import com.project.security.model.dto.response.SignUpResponseDto;
import com.project.security.model.dto.response.TokenResponseDto;

public interface AuthService {

  TokenResponseDto signIn(LoginRequestDto loginRequest);

  SignUpResponseDto signUp(SignUpRequestDto signUpRequest);
}
