package com.guillermoinc.training.service;

import com.guillermoinc.training.dto.UsuarioResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService {
    
    UserDetailsService userDetailsService();
    
    UsuarioResponseDto getUserProfile(String email);
}