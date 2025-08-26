package com.guillermoinc.training.service.impl;

import com.guillermoinc.training.dto.UsuarioResponseDto;
import com.guillermoinc.training.entity.Usuario;
import com.guillermoinc.training.repository.UsuarioRepository;
import com.guillermoinc.training.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    @Override
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
    
    @Override
    public UsuarioResponseDto getUserProfile(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        
        return UsuarioResponseDto.builder()
                .id(usuario.getIdUsuario())
                .email(usuario.getEmail())
                .firstName(usuario.getFirstName())
                .lastName(usuario.getLastName())
                .role(usuario.getRole())
                .department(usuario.getDepartment())
                .profilePicture(usuario.getProfilePicture())
                .joinDate(usuario.getJoinDate())
                .lastActive(usuario.getLastActive())
                .enrolledCourses(new ArrayList<>()) // TODO: Implementar lógica real
                .completedCourses(new ArrayList<>()) // TODO: Implementar lógica real
                .badges(new ArrayList<>()) // TODO: Implementar lógica real
                .build();
    }
}