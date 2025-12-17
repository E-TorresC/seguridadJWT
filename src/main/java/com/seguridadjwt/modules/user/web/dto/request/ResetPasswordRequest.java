package com.seguridadjwt.modules.user.web.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class ResetPasswordRequest {

  @NotBlank
  String token;

  @NotBlank
  @Size(min = 6, max = 100)
  String newPassword;
}