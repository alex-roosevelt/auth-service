package com.project.security.controller;

import com.project.security.exceptions.UserNotFoundException;
import com.project.security.model.dto.request.UserRequestDto;
import com.project.security.model.dto.response.UserResponseDto;
import com.project.security.service.AdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  @PostMapping("user/create")
  public ResponseEntity<UserResponseDto> authenticateUser(
      @RequestBody UserRequestDto userRequest) {
    return ResponseEntity.ok(adminService.createUser(userRequest));
  }

  @PutMapping("user/update/{userId}")
  public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId,
      @RequestBody UserRequestDto userRequest)
      throws UserNotFoundException {
    return ResponseEntity.ok(adminService.updateUser(userRequest, userId));
  }

  @GetMapping("user/list")
  public ResponseEntity<?> getListUsers() {
    return ResponseEntity.ok(adminService.getAllUsers());
  }

  @GetMapping("user/{userId}")
  public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId)
      throws UserNotFoundException {
    return ResponseEntity.ok(adminService.findById(userId));
  }

  @DeleteMapping("user/delete/{userId}")
  public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
    adminService.deleteUser(userId);
    return ResponseEntity.accepted().build();
  }

  @DeleteMapping("user/delete")
  public ResponseEntity<?> registerUser(@RequestParam List<Long> userIds) {
    adminService.deleteAllUsers(userIds);
    return ResponseEntity.accepted().build();
  }
}
