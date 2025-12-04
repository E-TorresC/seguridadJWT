package com.seguridadjwt.modules.user.web.dto.response;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.seguridadjwt.shared.security.UserStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
@JsonPropertyOrder({
  "id", "username", "email", "firstName", "lastName", "status",
  "roles", "createdAt", "createdBy", "updatedAt", "updatedBy"
})
public class UserResponse {

  Long id;
  String username;
  String email;
  String firstName;
  String lastName;
  UserStatus status;
  Set<String> roles;
  LocalDateTime createdAt;
  String createdBy;
  LocalDateTime updatedAt;
  String updatedBy;
}
