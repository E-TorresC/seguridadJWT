package com.seguridadjwt.modules.user.web.controller;

import com.seguridadjwt.modules.user.web.dto.request.LoginRequest;
import com.seguridadjwt.modules.user.web.dto.request.RefreshTokenRequest;
import com.seguridadjwt.modules.user.web.dto.response.LoginResponse;
import com.seguridadjwt.shared.exceptions.BusinessException;
import com.seguridadjwt.shared.security.CustomUserDetails;
import com.seguridadjwt.shared.security.CustomUserDetailsService;
import com.seguridadjwt.shared.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final CustomUserDetailsService customUserDetailsService;

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

      CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

      LoginResponse response = LoginResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .username(principal.getUsername())
        .email(principal.getEmail())
        .roles(
          authentication.getAuthorities().stream()
            .map(a -> a.getAuthority())
            .collect(Collectors.toSet())
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

    // Cargar usuario real desde BD para reconstruir authorities (roles)
    var userDetails = customUserDetailsService.loadUserByUsername(username);

    Authentication authentication = new UsernamePasswordAuthenticationToken(
      userDetails,
      null,
      userDetails.getAuthorities()
    );

    String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);

    LoginResponse response = LoginResponse.builder()
      .accessToken(newAccessToken)
      .refreshToken(refreshToken) // (opcional: rotar refresh token, no lo cambio aquí)
      .username(userDetails.getUsername())
      .email(userDetails instanceof CustomUserDetails cud ? cud.getEmail() : null)
      .roles(
        userDetails.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.toSet())
      )
      .build();

    return ResponseEntity.ok(response);
  }
}
