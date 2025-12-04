package com.seguridadjwt.shared.security;

import com.seguridadjwt.modules.user.domain.entity.User;
import com.seguridadjwt.modules.user.domain.repository.UserRepository;
import com.seguridadjwt.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
      .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + usernameOrEmail));
    return new CustomUserDetails(user);
  }
}
