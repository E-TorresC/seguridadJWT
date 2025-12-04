package com.seguridadjwt.modules.borrador;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPassword {
  public static void main(String[] args) {
    String raw = "Admin123x"; // la que estás enviando desde Postman
    String encoded = "$2a$10$KUGa7w8g9R5h4g3p9sQV..."; // copia EXACTA de tu BD

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    System.out.println(encoder.matches(raw, encoded)); // debe imprimir true o false
  }
}
