package com.seguridadjwt.modules.user.web.mapper;


import com.seguridadjwt.modules.user.domain.entity.Role;
import com.seguridadjwt.modules.user.domain.entity.User;
import com.seguridadjwt.modules.user.web.dto.request.UserCreateRequest;
import com.seguridadjwt.modules.user.web.dto.request.UserUpdateRequest;
import com.seguridadjwt.modules.user.web.dto.response.UserResponse;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", ignore = true) // se setea en el servicio
  @Mapping(target = "status", expression = "java(com.seguridadjwt.shared.security.UserStatus.ACTIVE)")
  @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  @Mapping(target = "failedAttempts", expression = "java(0)")
  @Mapping(target = "lastLoginAt", ignore = true)
  @Mapping(target = "lastFailedAt", ignore = true)
  @Mapping(target = "roles", ignore = true)
  User toEntity(UserCreateRequest request);

  @Mapping(target = "roles", expression = "java(mapRoleNames(user))")
  UserResponse toResponse(User user);

  List<UserResponse> toResponseList(List<User> users);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "roles", ignore = true)
  void updateEntityFromRequest(UserUpdateRequest request, @MappingTarget User user);

  default Set<String> mapRoleNames(User user) {
    if (user.getRoles() == null) {
      return Set.of();
    }
    return user.getRoles().stream()
      .map(Role::getName)
      .collect(Collectors.toSet());
  }
}
