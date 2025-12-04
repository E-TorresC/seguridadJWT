package com.seguridadjwt.modules.user.domain.service;

import com.seguridadjwt.modules.user.web.dto.request.RoleRequest;
import com.seguridadjwt.modules.user.web.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

  RoleResponse create(RoleRequest request, String currentUser);

  RoleResponse update(Long id, RoleRequest request, String currentUser);

  RoleResponse findById(Long id);

  List<RoleResponse> findAll();

  void delete(Long id);
}
