package com.seguridadjwt.shared.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

  private final SecretKey secretKey;
  private final long accessTokenValidityMs;
  private final long refreshTokenValidityMs;

  public JwtTokenProvider(
    @Value("${security.jwt.secret}") String secret,
    @Value("${security.jwt.access-token-validity-ms:900000}") long accessTokenValidityMs,
    @Value("${security.jwt.refresh-token-validity-ms:604800000}") long refreshTokenValidityMs
  ) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    this.accessTokenValidityMs = accessTokenValidityMs;
    this.refreshTokenValidityMs = refreshTokenValidityMs;
  }

  public String generateAccessToken(Authentication authentication) {
    String username = authentication.getName();

    String roles = authentication.getAuthorities().stream()
      .map(auth -> auth.getAuthority())
      .collect(Collectors.joining(","));

    Instant now = Instant.now();
    Instant expiry = now.plusMillis(accessTokenValidityMs);

    return Jwts.builder()
      .setSubject(username)
      .claim("roles", roles)
      .setIssuedAt(Date.from(now))
      .setExpiration(Date.from(expiry))
      .signWith(secretKey, SignatureAlgorithm.HS256)
      .compact();
  }

  public String generateRefreshToken(Authentication authentication) {
    String username = authentication.getName();
    Instant now = Instant.now();
    Instant expiry = now.plusMillis(refreshTokenValidityMs);

    return Jwts.builder()
      .setSubject(username)
      .setIssuedAt(Date.from(now))
      .setExpiration(Date.from(expiry))
      .signWith(secretKey, SignatureAlgorithm.HS256)
      .compact();
  }

  public String getUsernameFromToken(String token) {
    return parseClaims(token).getBody().getSubject();
  }

  public boolean validateToken(String token) {
    try {
      parseClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException ex) {
      return false;
    }
  }

  private Jws<Claims> parseClaims(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(secretKey)
      .build()
      .parseClaimsJws(token);
  }
}
