package com.guillermoinc.training.controller;

import com.guillermoinc.training.dto.CapituloConCursoResponseDto;
import com.guillermoinc.training.dto.CursoResponseDto;
import com.guillermoinc.training.dto.UsuarioResponseDto;
import com.guillermoinc.training.service.UsuarioCursoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario-cursos")
@RequiredArgsConstructor
@Slf4j
public class UsuarioCursoController {
    
    private final UsuarioCursoService usuarioCursoService;
    
    @GetMapping("/usuario/{userId}/cursos")
    public ResponseEntity<List<CursoResponseDto>> obtenerCursosDeUsuario(
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authorizationToken) {
        
        log.info("Solicitud para obtener cursos del usuario ID: {}", userId);
        
        List<CursoResponseDto> cursos = usuarioCursoService.obtenerCursosDeUsuario(userId, authorizationToken);
        
        return ResponseEntity.ok(cursos);
    }
    
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<UsuarioResponseDto> obtenerUsuario(
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authorizationToken) {
        
        log.info("Solicitud para obtener información del usuario ID: {}", userId);
        
        UsuarioResponseDto usuario = usuarioCursoService.obtenerUsuarioPorId(userId, authorizationToken);
        
        return ResponseEntity.ok(usuario);
    }
    
    @GetMapping("/usuario/{userId}/capitulos")
    public ResponseEntity<List<CapituloConCursoResponseDto>> obtenerCapitulosDeUsuario(
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authorizationToken) {
        
        log.info("Solicitud para obtener capítulos del usuario ID: {}", userId);
        
        List<CapituloConCursoResponseDto> capitulos = usuarioCursoService.obtenerCapitulosDeUsuario(userId, authorizationToken);
        
        return ResponseEntity.ok(capitulos);
    }
}