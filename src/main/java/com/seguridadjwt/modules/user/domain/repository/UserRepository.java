package com.seguridadjwt.modules.user.domain.repository;

import com.seguridadjwt.modules.user.domain.entity.User;
import com.seguridadjwt.shared.security.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  Optional<User> findByUsernameOrEmail(String username, String email);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  long countByStatus(UserStatus status);
}
