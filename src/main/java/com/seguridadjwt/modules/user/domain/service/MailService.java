package com.seguridadjwt.modules.user.domain.service;


public interface MailService {
  void send(String to, String subject, String body);
}
