package com.seguridadjwt.modules.user.web.controller;


import com.seguridadjwt.modules.user.domain.service.UserService;
import com.seguridadjwt.modules.user.web.dto.request.UserCreateRequest;
import com.seguridadjwt.modules.user.web.dto.request.UserUpdateRequest;
import com.seguridadjwt.modules.user.web.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserResponse> create(
    @Valid @RequestBody UserCreateRequest request,
    @AuthenticationPrincipal UserDetails currentUser
  ) {
    UserResponse response = userService.create(
      request,
      currentUser != null ? currentUser.getUsername() : "SYSTEM"
    );
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(
    @PathVariable Long id,
    @Valid @RequestBody UserUpdateRequest request,
    @AuthenticationPrincipal UserDetails currentUser
  ) {
    UserResponse response = userService.update(
      id, request,
      currentUser != null ? currentUser.getUsername() : "SYSTEM"
    );
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{id}/status/{status}")
  public ResponseEntity<Void> changeStatus(
    @PathVariable Long id,
    @PathVariable String status,
    @AuthenticationPrincipal UserDetails currentUser
  ) {
    userService.changeStatus(id, status, currentUser.getUsername());
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  @GetMapping
  public ResponseEntity<List<UserResponse>> findAll() {
    return ResponseEntity.ok(userService.findAll());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteLogical(
    @PathVariable Long id,
    @AuthenticationPrincipal UserDetails currentUser
  ) {
    userService.deleteLogical(id, currentUser.getUsername());
    return ResponseEntity.noContent().build();
  }
}
