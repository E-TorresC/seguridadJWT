package com.seguridadjwt.modules.user.web.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class LoginRequest {

  @NotBlank
  String usernameOrEmail;

  @NotBlank
  String password;
}