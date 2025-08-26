// package com.guillermoinc.training.service.impl;

// import com.guillermoinc.training.dto.LoginRequestDto;
// import com.guillermoinc.training.dto.LoginResponseDto;
// import com.guillermoinc.training.dto.RegisterRequestDto;
// import com.guillermoinc.training.dto.UsuarioResponseDto;
// import com.guillermoinc.training.entity.Role;
// import com.guillermoinc.training.entity.Usuario;
// import com.guillermoinc.training.repository.UsuarioRepository;
// import com.guillermoinc.training.service.AuthService;
// import com.guillermoinc.training.service.JwtService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service
// @RequiredArgsConstructor
// public class AuthServiceImpl implements AuthService {
    
//     private final UsuarioRepository usuarioRepository;
//     private final PasswordEncoder passwordEncoder;
//     private final JwtService jwtService;
//     private final AuthenticationManager authenticationManager;
    
//     @Override
//     public LoginResponseDto login(LoginRequestDto request) {
//         authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(
//                         request.getEmail(),
//                         request.getPassword()
//                 )
//         );
        
//         var usuario = usuarioRepository.findByEmail(request.getEmail())
//                 .orElseThrow();
        
//         var jwtToken = jwtService.generateToken(usuario);
        
//         return LoginResponseDto.builder()
//                 .token(jwtToken)
//                 .type("Bearer")
//                 .user(buildUsuarioResponseDto(usuario))
//                 .build();
//     }
    
//     @Override
//     public LoginResponseDto register(RegisterRequestDto request) {
//         if (usuarioRepository.existsByEmail(request.getEmail())) {
//             throw new RuntimeException("El usuario ya existe");
//         }
        
//         var usuario = Usuario.builder()
//                 .email(request.getEmail())
//                 .firstName(request.getFirstName())
//                 .lastName(request.getLastName())
//                 .password(passwordEncoder.encode(request.getPassword()))
//                 .role(Role.STUDENT) // Por defecto es estudiante
//                 .department(request.getDepartment())
//                 .enabled(true)
//                 .accountNonExpired(true)
//                 .accountNonLocked(true)
//                 .credentialsNonExpired(true)
//                 .build();
        
//         usuarioRepository.save(usuario);
        
//         var jwtToken = jwtService.generateToken(usuario);
        
//         return LoginResponseDto.builder()
//                 .token(jwtToken)
//                 .type("Bearer")
//                 .user(buildUsuarioResponseDto(usuario))
//                 .build();
//     }
    
//     private UsuarioResponseDto buildUsuarioResponseDto(Usuario usuario) {
//         return UsuarioResponseDto.builder()
//                 .id(usuario.getIdUsuario())
//                 .email(usuario.getEmail())
//                 .firstName(usuario.getFirstName())
//                 .lastName(usuario.getLastName())
//                 .role(usuario.getRole())
//                 .department(usuario.getDepartment())
//                 .profilePicture(usuario.getProfilePicture())
//                 .joinDate(usuario.getJoinDate())
//                 .lastActive(usuario.getLastActive())
//                 .build();
//     }
// }