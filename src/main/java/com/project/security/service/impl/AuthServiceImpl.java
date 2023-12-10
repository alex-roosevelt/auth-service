package com.project.security.service.impl;

import com.project.security.config.jwt.JwtTokenUtil;
import com.project.security.model.dto.request.LoginRequestDto;
import com.project.security.model.dto.request.SignUpRequestDto;
import com.project.security.model.dto.response.SignUpResponseDto;
import com.project.security.model.dto.response.TokenResponseDto;
import com.project.security.model.entity.Role;
import com.project.security.model.entity.User;
import com.project.security.repository.RoleRepository;
import com.project.security.repository.UserRepository;
import com.project.security.service.AuthService;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenUtil jwtTokenUtil;

  @Override
  public TokenResponseDto signIn(LoginRequestDto loginRequest) {
    Optional<User> userOptional = userRepository.findByUserName(loginRequest.getUsername());
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));
    if (authentication.isAuthenticated() && userOptional.isPresent()) {
      String jwt = jwtTokenUtil.generateToken(userOptional.get().getUserName(),
          userOptional.get().getRoles().stream().map(
              Role::getName).collect(
              Collectors.toList()));
      return TokenResponseDto
          .builder()
          .jwtToken(jwt)
          .build();
    } else {
      throw new UsernameNotFoundException("invalid user request !");
    }
  }

  @Override
  @Transactional
  public SignUpResponseDto signUp(SignUpRequestDto signUpRequest) {
    // checking for username exists in a database
    if (userRepository.existsByUserName(signUpRequest.getUserName())) {
      return SignUpResponseDto.builder()
          .message("User is already exists!")
          .code(HttpStatus.BAD_REQUEST.name())
          .build();
    }
    // checking for email exists in a database
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return SignUpResponseDto.builder()
          .message("Email is already exists!")
          .code(HttpStatus.BAD_REQUEST.name())
          .build();
    }
    // creating user object
    User user = new User();
    user.setFullName(signUpRequest.getFullName());
    user.setUserName(signUpRequest.getUserName());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
    Role roles = roleRepository.findByName("ROLE_USER").get();
    user.setRoles(Collections.singleton(roles));
    userRepository.save(user);
    return SignUpResponseDto.builder()
        .message("User is saved!")
        .code(HttpStatus.OK.name())
        .build();
  }
}
