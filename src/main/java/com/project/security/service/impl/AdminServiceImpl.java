package com.project.security.service.impl;

import com.project.security.enums.RoleEnum;
import com.project.security.exceptions.UserNotFoundException;
import com.project.security.model.dto.request.UserRequestDto;
import com.project.security.model.dto.response.UserResponseDto;
import com.project.security.model.entity.Role;
import com.project.security.model.entity.User;
import com.project.security.repository.RoleRepository;
import com.project.security.repository.UserRepository;
import com.project.security.service.AdminService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserResponseDto createUser(UserRequestDto userRequest) {
    User user = new User();
    user.setFullName(userRequest.getFullName());
    user.setUserName(userRequest.getUserName());
    user.setEmail(userRequest.getEmail());
    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    if (!userRequest.getRoles().isEmpty()) {
      List<Role> roles = new ArrayList<>();
      for (String role : userRequest.getRoles()) {
        roles.add(roleRepository.findByName(role).orElse(null));
      }
      user.setRoles(new HashSet<>(roles));
    } else {
      user.setRoles(
          Collections.singleton(roleRepository.findByName(RoleEnum.USER.name()).get()));
    }

    User savedUser = userRepository.save(user);

    return UserResponseDto
        .builder()
        .id(savedUser.getId().toString())
        .email(savedUser.getEmail())
        .userName(savedUser.getUserName())
        .fullName(savedUser.getFullName())
        .roles(
            savedUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
        .build();
  }

  @Override
  @Transactional
  public UserResponseDto updateUser(UserRequestDto userRequest, Long userId)
      throws UserNotFoundException {

    Optional<User> userOptional = userRepository.findById(userId);

    if (userOptional.isEmpty()) {
      throw new UserNotFoundException(
          String.format("User with id - %s and username %s not found", userId,
              userRequest.getUserName()));
    }

    User findedUser = userOptional.get();
    findedUser.setFullName(userRequest.getFullName());
    findedUser.setUserName(userRequest.getUserName());
    findedUser.setEmail(userRequest.getEmail());

    if (!userRequest.getRoles().isEmpty()) {
      List<Role> roles = new ArrayList<>();
      for (String role : userRequest.getRoles()) {
        roles.add(roleRepository.findByName(role).orElse(null));
      }
      findedUser.setRoles(new HashSet<>(roles));
    } else {
      findedUser.setRoles(
          Collections.singleton(roleRepository.findByName(RoleEnum.USER.name()).get()));
    }

    User savedUser = userRepository.save(findedUser);

    return UserResponseDto
        .builder()
        .id(savedUser.getId().toString())
        .email(savedUser.getEmail())
        .userName(savedUser.getUserName())
        .fullName(savedUser.getFullName())
        .roles(
            savedUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
        .build();
  }

  @Override
  @Transactional
  public List<UserResponseDto> getAllUsers() {
    List<User> users = userRepository.findAll();
    if (users.isEmpty()) {
      return new ArrayList<>();
    }
    return users.stream()
        .map(user -> UserResponseDto
            .builder()
            .id(user.getId().toString())
            .email(user.getEmail())
            .userName(user.getUserName())
            .fullName(user.getFullName())
            .roles(
                user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
            .build()).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public UserResponseDto findById(Long userId) throws UserNotFoundException {
    return userRepository.findById(userId).map(user -> UserResponseDto
        .builder()
        .id(user.getId().toString())
        .email(user.getEmail())
        .userName(user.getUserName())
        .fullName(user.getFullName())
        .roles(
            user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
        .build()).orElseThrow(
        () -> new UserNotFoundException(String.format("User with id - %s not found !", userId)));
  }

  @Override
  @Transactional
  public void deleteUser(Long userId) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isPresent()) {
      userRepository.deleteById(userId);
    }
  }

  @Override
  @Transactional
  public void deleteAllUsers(List<Long> userIds) {
    List<User> userList = userRepository.findAllById(userIds);
    if (!userList.isEmpty()) {
      userRepository.deleteAllById(userIds);
    }
  }
}
