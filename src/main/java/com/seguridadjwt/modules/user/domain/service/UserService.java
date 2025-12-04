package com.seguridadjwt.modules.user.domain.service;


import com.seguridadjwt.modules.user.web.dto.request.UserCreateRequest;
import com.seguridadjwt.modules.user.web.dto.request.UserUpdateRequest;
import com.seguridadjwt.modules.user.web.dto.response.UserResponse;

import java.util.List;

public interface UserService {

  UserResponse create(UserCreateRequest request, String currentUser);

  UserResponse update(Long id, UserUpdateRequest request, String currentUser);

  void changeStatus(Long id, String status, String currentUser);

  UserResponse findById(Long id);

  List<UserResponse> findAll();

  void deleteLogical(Long id, String currentUser);
}
