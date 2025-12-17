package com.seguridadjwt.modules.user.domain.repository;


import com.seguridadjwt.modules.user.domain.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

  Optional<PasswordResetToken> findByTokenHash(String tokenHash);

  long deleteByExpiresAtBefore(LocalDateTime time);
}
