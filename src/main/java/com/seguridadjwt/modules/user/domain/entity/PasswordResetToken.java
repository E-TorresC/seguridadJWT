package com.seguridadjwt.modules.user.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
  name = "password_reset_tokens",
  indexes = {
    @Index(name = "idx_prt_token_hash", columnList = "token_hash"),
    @Index(name = "idx_prt_expires_at", columnList = "expires_at")
  }
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "token_hash", nullable = false, length = 64, unique = true)
  private String tokenHash; // SHA-256 hex (64 chars)

  @Column(name = "expires_at", nullable = false)
  private LocalDateTime expiresAt;

  @Column(name = "used_at")
  private LocalDateTime usedAt;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "request_ip", length = 60)
  private String requestIp;

  @Column(name = "user_agent", length = 300)
  private String userAgent;

  public boolean isUsed() {
    return usedAt != null;
  }

  public boolean isExpired(LocalDateTime now) {
    return expiresAt.isBefore(now);
  }
}
