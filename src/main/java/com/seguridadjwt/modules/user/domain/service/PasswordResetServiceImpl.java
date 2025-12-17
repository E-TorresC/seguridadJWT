package com.seguridadjwt.modules.user.domain.service;

import com.seguridadjwt.modules.user.domain.entity.PasswordResetToken;
import com.seguridadjwt.modules.user.domain.entity.User;
import com.seguridadjwt.modules.user.domain.repository.PasswordResetTokenRepository;
import com.seguridadjwt.modules.user.domain.repository.UserRepository;

import com.seguridadjwt.shared.security.TokenUtils;
import com.seguridadjwt.shared.security.UserStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordResetServiceImpl implements PasswordResetService {

  private final UserRepository userRepository;
  private final PasswordResetTokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final MailService mailService;

  @Value("${app.frontend.reset-url:http://localhost:4200/reset-password?token={token}}")
  private String resetUrlTemplate;

  // 15 min (puede moverlo a properties)
  private static final int TOKEN_MINUTES = 15;

  @Override
  public void requestReset(String email, HttpServletRequest request) {

    // Respuesta “silenciosa”: aunque no exista email, no revelamos.
    userRepository.findByEmail(email).ifPresent(user -> {
      if (user.getStatus() != UserStatus.ACTIVE) {
        return; // no enviamos reset a inactivos/bloqueados
      }

      String rawToken = TokenUtils.generateToken();
      String tokenHash = TokenUtils.sha256Hex(rawToken);

      PasswordResetToken prt = PasswordResetToken.builder()
        .user(user)
        .tokenHash(tokenHash)
        .createdAt(LocalDateTime.now())
        .expiresAt(LocalDateTime.now().plusMinutes(TOKEN_MINUTES))
        .requestIp(extractIp(request))
        .userAgent(extractUserAgent(request))
        .build();

      tokenRepository.save(prt);

      String url = resetUrlTemplate.replace("{token}", rawToken);
      String subject = "Restablecimiento de contraseña";
      String body = """
        Se ha solicitado restablecer su contraseña.

        Para continuar, abra el siguiente enlace (válido por %d minutos):
        %s

        Si usted no solicitó este cambio, ignore este correo.
        """.formatted(TOKEN_MINUTES, url);

      mailService.send(user.getEmail(), subject, body);
    });
  }

  @Override
  public void resetPassword(String token, String newPassword) {

    String tokenHash = TokenUtils.sha256Hex(token);

    PasswordResetToken prt = tokenRepository.findByTokenHash(tokenHash)
      .orElseThrow(() -> new IllegalArgumentException("Token inválido")); // no revelar demasiado

    LocalDateTime now = LocalDateTime.now();

    if (prt.isUsed() || prt.isExpired(now)) {
      throw new IllegalArgumentException("Token inválido o expirado");
    }

    User user = prt.getUser();
    if (user.getStatus() != UserStatus.ACTIVE) {
      throw new IllegalArgumentException("No se puede restablecer para este usuario");
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    user.setUpdatedAt(now);
    user.setUpdatedBy("PASSWORD_RESET");
    user.setFailedAttempts(0);

    prt.setUsedAt(now);

    // Por estar en @Transactional, se persiste todo
  }

  private String extractIp(HttpServletRequest request) {
    String xff = request.getHeader("X-Forwarded-For");
    if (xff != null && !xff.isBlank()) return xff.split(",")[0].trim();
    return request.getRemoteAddr();
  }

  private String extractUserAgent(HttpServletRequest request) {
    String ua = request.getHeader("User-Agent");
    return (ua == null) ? null : ua.substring(0, Math.min(ua.length(), 300));
  }
}

