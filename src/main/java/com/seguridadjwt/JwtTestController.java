package com.seguridadjwt;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test/productos")
public class JwtTestController {

  @GetMapping
  public ResponseEntity<List<Map<String, Object>>> listarProductosProtegidos() {

    List<Map<String, Object>> productos = List.of(
      Map.of(
        "id", 1,
        "nombre", "Mesa de comedor",
        "precio", 850.00
      ),
      Map.of(
        "id", 2,
        "nombre", "Silla de madera",
        "precio", 180.00
      ),
      Map.of(
        "id", 3,
        "nombre", "Ropero moderno",
        "precio", 1200.00
      )
    );

    return ResponseEntity.ok(productos);
  }
}
