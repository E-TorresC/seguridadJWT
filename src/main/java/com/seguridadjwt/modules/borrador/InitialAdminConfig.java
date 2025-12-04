//package com.seguridadjwt.modules.borrador;
//
//
//import com.seguridadjwt.modules.user.domain.entity.Role;
//import com.seguridadjwt.modules.user.domain.entity.User;
//import com.seguridadjwt.modules.user.domain.repository.RoleRepository;
//import com.seguridadjwt.modules.user.domain.repository.UserRepository;
//import com.seguridadjwt.shared.security.UserStatus;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDateTime;
//import java.util.Set;
//
//@Configuration
//@RequiredArgsConstructor
//public class InitialAdminConfig {
//
//  private final UserRepository userRepository;
//  private final RoleRepository roleRepository;
//  private final PasswordEncoder passwordEncoder;
//
//  @Bean
//  public CommandLineRunner initAdminUser() {
//    return args -> {
//
//      // ------------------------------
//      // 1. Verificar si ya existe admin
//      // ------------------------------
//      if (userRepository.existsByUsername("admin")) {
//        System.out.println("✔ Usuario admin ya existe, no se creará uno nuevo.");
//        return;
//      }
//
//      System.out.println("⚠ Usuario admin no existe. Creando admin inicial...");
//
//      // ------------------------------
//      // 2. Verificar o crear ROLE_ADMIN
//      // ------------------------------
//      Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
//        .orElseGet(() -> {
//          Role r = new Role();
//          r.setName("ROLE_ADMIN");
//          r.setDescription("Administrador del sistema");
//          r.setCreatedAt(LocalDateTime.now());
//          System.out.println("⚠ Rol ROLE_ADMIN no existe. Creando...");
//          return roleRepository.save(r);
//        });
//
//      // ------------------------------
//      // 3. Crear usuario admin
//      // ------------------------------
//      User admin = new User();
//      admin.setUsername("admin");
//      admin.setEmail("admin@muebleriamg.com");
//      admin.setPassword(passwordEncoder.encode("admin123")); // contraseña real
//      admin.setStatus(UserStatus.ACTIVE);
//      admin.setFirstName("Usuario");
//      admin.setLastName("Administrador");
//      admin.setCreatedAt(LocalDateTime.now());
//      admin.setFailedAttempts(0);
//      admin.setCreatedBy("SYSTEM");
//      admin.setRoles(Set.of(roleAdmin));
//
//      userRepository.save(admin);
//
//      System.out.println("✔ Usuario admin creado correctamente.");
//    };
//  }
//}
