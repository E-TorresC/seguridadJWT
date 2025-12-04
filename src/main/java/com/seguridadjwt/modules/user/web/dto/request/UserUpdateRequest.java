package com.seguridadjwt.modules.user.web.dto.request;

import com.seguridadjwt.shared.security.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Set;

@Value
public class UserUpdateRequest {

  @NotBlank
  @Size(max = 100)
  String firstName;

  @NotBlank
  @Size(max = 100)
  String lastName;

  @Email
  @Size(max = 100)
  String email;

  UserStatus status;

  Set<@NotBlank String> roles;
}
