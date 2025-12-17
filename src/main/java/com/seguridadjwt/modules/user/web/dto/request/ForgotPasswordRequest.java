package com.seguridadjwt.modules.user.web.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class ForgotPasswordRequest {
  @NotBlank
  @Email
  String email;
}
