// package com.guillermoinc.training.controller;

// import com.guillermoinc.training.dto.LoginRequestDto;
// import com.guillermoinc.training.dto.LoginResponseDto;
// import com.guillermoinc.training.dto.RegisterRequestDto;
// import com.guillermoinc.training.dto.UsuarioResponseDto;
// import com.guillermoinc.training.service.AuthService;
// import com.guillermoinc.training.service.UsuarioService;
// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/auth")
// @RequiredArgsConstructor
// @CrossOrigin(origins = "*")
// public class AuthController {
    
//     private final AuthService authService;
//     private final UsuarioService usuarioService;
    
//     @PostMapping("/login")
//     public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
//         try {
//             LoginResponseDto response = authService.login(request);
//             return ResponseEntity.ok(response);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                     .body(LoginResponseDto.builder()
//                             .token(null)
//                             .type("error")
//                             .user(null)
//                             .build());
//         }
//     }
    
//     @PostMapping("/register")
//     public ResponseEntity<LoginResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
//         try {
//             LoginResponseDto response = authService.register(request);
//             return new ResponseEntity<>(response, HttpStatus.CREATED);
//         } catch (Exception e) {
//             return ResponseEntity.badRequest()
//                     .body(LoginResponseDto.builder()
//                             .token(null)
//                             .type("error")
//                             .user(null)
//                             .build());
//         }
//     }
    
//     @GetMapping("/profile")
//     public ResponseEntity<UsuarioResponseDto> getProfile(Authentication authentication) {
//         try {
//             String email = authentication.getName();
//             UsuarioResponseDto profile = usuarioService.getUserProfile(email);
//             return ResponseEntity.ok(profile);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//         }
//     }
    
//     @PostMapping("/logout")
//     public ResponseEntity<Void> logout() {
//         // Con JWT stateless, el logout se maneja en el frontend eliminando el token
//         return ResponseEntity.ok().build();
//     }
// }