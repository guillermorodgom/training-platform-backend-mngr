package com.guillermoinc.training.service;

import com.guillermoinc.training.dto.CapituloConCursoResponseDto;
import com.guillermoinc.training.dto.CursoResponseDto;
import com.guillermoinc.training.dto.UsuarioResponseDto;

import java.util.List;

public interface UsuarioCursoService {
    
    List<CursoResponseDto> obtenerCursosDeUsuario(Integer userId, String authorizationToken);
    
    UsuarioResponseDto obtenerUsuarioPorId(Integer userId, String authorizationToken);
    
    List<CapituloConCursoResponseDto> obtenerCapitulosDeUsuario(Integer userId, String authorizationToken);
}