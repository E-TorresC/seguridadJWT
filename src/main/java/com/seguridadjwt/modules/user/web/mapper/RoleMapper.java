package com.seguridadjwt.modules.user.web.mapper;

import com.seguridadjwt.modules.user.domain.entity.Role;
import com.seguridadjwt.modules.user.web.dto.request.RoleRequest;
import com.seguridadjwt.modules.user.web.dto.response.RoleResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)//expression = "java(LocalDateTime.now())")
  @Mapping(target = "updatedAt", ignore = true)
  Role toEntity(RoleRequest request);

  RoleResponse toResponse(Role role);

  List<RoleResponse> toResponseList(List<Role> roles);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "createdAt", ignore = true)
  void updateEntityFromRequest(RoleRequest request, @MappingTarget Role role);
}
