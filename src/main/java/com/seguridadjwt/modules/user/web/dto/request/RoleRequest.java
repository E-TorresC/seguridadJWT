package com.seguridadjwt.modules.user.web.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class RoleRequest {

  @NotBlank
  @Size(max = 50)
  String name;

  @Size(max = 255)
  String description;
}
