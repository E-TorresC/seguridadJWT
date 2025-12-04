package com.seguridadjwt.modules.user.domain.service;


import com.seguridadjwt.modules.user.domain.entity.Role;
import com.seguridadjwt.modules.user.domain.repository.RoleRepository;
import com.seguridadjwt.modules.user.web.dto.request.RoleRequest;
import com.seguridadjwt.modules.user.web.dto.response.RoleResponse;
import com.seguridadjwt.modules.user.web.mapper.RoleMapper;
import com.seguridadjwt.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  @Override
  public RoleResponse create(RoleRequest request, String currentUser) {
    Role role = roleMapper.toEntity(request);
    role.setCreatedAt(LocalDateTime.now());
    Role saved = roleRepository.save(role);
    return roleMapper.toResponse(saved);
  }

  @Override
  public RoleResponse update(Long id, RoleRequest request, String currentUser) {
    Role role = roleRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    roleMapper.updateEntityFromRequest(request, role);
    role.setUpdatedAt(LocalDateTime.now());
    Role saved = roleRepository.save(role);
    return roleMapper.toResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public RoleResponse findById(Long id) {
    Role role = roleRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    return roleMapper.toResponse(role);
  }

  @Override
  @Transactional(readOnly = true)
  public List<RoleResponse> findAll() {
    return roleMapper.toResponseList(roleRepository.findAll());
  }

  @Override
  public void delete(Long id) {
    if (!roleRepository.existsById(id)) {
      throw new ResourceNotFoundException("Role not found with id: " + id);
    }
    roleRepository.deleteById(id);
  }
}
