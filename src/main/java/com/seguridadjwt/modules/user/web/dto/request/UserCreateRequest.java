package com.seguridadjwt.modules.user.web.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Set;

@Value
public class UserCreateRequest {

  @NotBlank
  @Size(min = 3, max = 50)
  String username;

  @NotBlank
  @Email
  @Size(max = 100)
  String email;

  @NotBlank
  @Size(min = 6, max = 100)
  String password;

  @NotBlank
  @Size(max = 100)
  String firstName;

  @NotBlank
  @Size(max = 100)
  String lastName;

  // Lista de nombres de rol, ej: ["ROLE_ADMIN", "ROLE_VENDEDOR"]
  Set<@NotBlank String> roles;
}