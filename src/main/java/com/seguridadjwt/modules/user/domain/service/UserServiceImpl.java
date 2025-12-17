package com.seguridadjwt.modules.user.domain.service;

import com.seguridadjwt.modules.user.domain.entity.Role;
import com.seguridadjwt.modules.user.domain.entity.User;
import com.seguridadjwt.modules.user.domain.repository.RoleRepository;
import com.seguridadjwt.modules.user.domain.repository.UserRepository;
import com.seguridadjwt.modules.user.web.dto.request.UserCreateRequest;
import com.seguridadjwt.modules.user.web.dto.request.UserUpdateRequest;
import com.seguridadjwt.modules.user.web.dto.response.UserResponse;
import com.seguridadjwt.modules.user.web.mapper.UserMapper;
import com.seguridadjwt.shared.exceptions.BusinessException;
import com.seguridadjwt.shared.exceptions.ResourceNotFoundException;
import com.seguridadjwt.shared.security.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserResponse create(UserCreateRequest request, String currentUser) {

    User user = userMapper.toEntity(request);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setCreatedBy(currentUser);

    // Defaults centralizados aquí (sin duplicidad con Mapper)
    user.setStatus(UserStatus.ACTIVE);
    user.setCreatedAt(LocalDateTime.now());
    user.setFailedAttempts(0);

    if (request.getRoles() != null && !request.getRoles().isEmpty()) {
      Set<Role> roles = new HashSet<>();
      for (String roleName : request.getRoles()) {
        Role role = roleRepository.findByName(roleName)
          .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName));
        roles.add(role);
      }
      user.setRoles(roles);
    }

    user.setCreatedAt(LocalDateTime.now());
    user.setFailedAttempts(0);

    User saved = userRepository.save(user);
    return userMapper.toResponse(saved);
  }

  @Override
  public UserResponse update(Long id, UserUpdateRequest request, String currentUser) {
    User user = userRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

    userMapper.updateEntityFromRequest(request, user);

    if (request.getStatus() != null) {
      user.setStatus(request.getStatus());
    }

    if (request.getRoles() != null) {
      Set<Role> roles = new HashSet<>();
      for (String roleName : request.getRoles()) {
        Role role = roleRepository.findByName(roleName)
          .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName));
        roles.add(role);
      }
      user.setRoles(roles);
    }

    user.setUpdatedAt(LocalDateTime.now());
    user.setUpdatedBy(currentUser);

    User saved = userRepository.save(user);
    return userMapper.toResponse(saved);
  }

  @Override
  public void changeStatus(Long id, String status, String currentUser) {
    User user = userRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

    try {
      UserStatus normalized = UserStatus.valueOf(status.trim().toUpperCase());
      user.setStatus(normalized);
    } catch (IllegalArgumentException ex) {
      throw new BusinessException("Estado inválido: " + status);
    }

    user.setUpdatedAt(LocalDateTime.now());
    user.setUpdatedBy(currentUser);
    userRepository.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse findById(Long id) {
    User user = userRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    return userMapper.toResponse(user);
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserResponse> findAll() {
    return userMapper.toResponseList(userRepository.findAll());
  }

  @Override
  public void deleteLogical(Long id, String currentUser) {
    User user = userRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    user.setStatus(UserStatus.INACTIVE);
    user.setUpdatedAt(LocalDateTime.now());
    user.setUpdatedBy(currentUser);
    userRepository.save(user);
  }
}
