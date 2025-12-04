package com.seguridadjwt.modules.user.web.controller;

import com.seguridadjwt.modules.user.domain.service.RoleService;
import com.seguridadjwt.modules.user.web.dto.request.RoleRequest;
import com.seguridadjwt.modules.user.web.dto.response.RoleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @PostMapping
  public ResponseEntity<RoleResponse> create(
    @Valid @RequestBody RoleRequest request,
    @AuthenticationPrincipal UserDetails currentUser
  ) {
    RoleResponse response = roleService.create(
      request,
      currentUser != null ? currentUser.getUsername() : "SYSTEM"
    );
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<RoleResponse> update(
    @PathVariable Long id,
    @Valid @RequestBody RoleRequest request,
    @AuthenticationPrincipal UserDetails currentUser
  ) {
    RoleResponse response = roleService.update(
      id, request,
      currentUser != null ? currentUser.getUsername() : "SYSTEM"
    );
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RoleResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(roleService.findById(id));
  }

  @GetMapping
  public ResponseEntity<List<RoleResponse>> findAll() {
    return ResponseEntity.ok(roleService.findAll());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    roleService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
