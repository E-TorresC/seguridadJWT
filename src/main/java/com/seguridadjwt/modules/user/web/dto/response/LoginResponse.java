package com.seguridadjwt.modules.user.web.dto.response;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
@JsonPropertyOrder({
  "accessToken", "refreshToken", "tokenType",
  "username", "email", "roles"
})
public class LoginResponse {

  String accessToken;
  String refreshToken;
  @Builder.Default
  String tokenType = "Bearer";
  String username;
  String email;
  Set<String> roles;
}
