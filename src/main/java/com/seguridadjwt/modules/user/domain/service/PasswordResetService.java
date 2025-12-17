package com.seguridadjwt.modules.user.domain.service;


import jakarta.servlet.http.HttpServletRequest;

public interface PasswordResetService {
  void requestReset(String email, HttpServletRequest request);
  void resetPassword(String token, String newPassword);
}
