package com.seguridadjwt.modules.user.web.dto.response;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@JsonPropertyOrder({ "id", "name", "description", "createdAt", "updatedAt" })
public class RoleResponse {

  Long id;
  String name;
  String description;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}