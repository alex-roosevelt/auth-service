package com.project.eneler.service;

import com.project.eneler.model.dto.request.LoginRequestDto;
import com.project.eneler.model.dto.request.SignUpRequestDto;
import com.project.eneler.model.dto.response.SignUpResponseDto;
import com.project.eneler.model.dto.response.TokenResponseDto;

public interface AuthService {

  TokenResponseDto signIn(LoginRequestDto loginRequest);

  SignUpResponseDto signUp(SignUpRequestDto signUpRequest);
}
