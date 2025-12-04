package com.seguridadjwt.modules.user.web.controller;

import com.seguridadjwt.modules.user.web.dto.request.LoginRequest;
import com.seguridadjwt.modules.user.web.dto.request.RefreshTokenRequest;
import com.seguridadjwt.modules.user.web.dto.response.LoginResponse;
import com.seguridadjwt.shared.exceptions.BusinessException;
import com.seguridadjwt.shared.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

    try {
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          request.getUsernameOrEmail(),
          request.getPassword()
        )
      );

      String accessToken = jwtTokenProvider.generateAccessToken(authentication);
      String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

      var principal = authentication.getPrincipal();
      String username = authentication.getName();
      String email = null;
      var authorities = authentication.getAuthorities();

      LoginResponse response = LoginResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .username(username)
        .email(email)
        .roles(
          authorities.stream()
            .map(a -> a.getAuthority())
            .collect(java.util.stream.Collectors.toSet())
        )
        .build();

      return ResponseEntity.ok(response);

    } catch (BadCredentialsException ex) {
      throw new BusinessException("Credenciales inválidas");
    }
  }

  @PostMapping("/refresh")
  public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {

    String refreshToken = request.getRefreshToken();

    if (!jwtTokenProvider.validateToken(refreshToken)) {
      throw new BadCredentialsException("Refresh token inválido o expirado");
    }

    String username = jwtTokenProvider.getUsernameFromToken(refreshToken);

    Authentication authentication = new UsernamePasswordAuthenticationToken(
      username,
      null,
      java.util.Collections.emptyList()
    );

    String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);

    LoginResponse response = LoginResponse.builder()
      .accessToken(newAccessToken)
      .refreshToken(refreshToken)
      .username(username)
      .build();

    return ResponseEntity.ok(response);
  }
}
