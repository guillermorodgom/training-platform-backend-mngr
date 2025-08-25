package com.guillermoinc.training.service;

import com.guillermoinc.training.dto.CursoRequestDto;
import com.guillermoinc.training.dto.CursoResponseDto;

import java.util.List;

public interface CursoService {
    
    CursoResponseDto crearCurso(CursoRequestDto cursoRequestDto);
    
    CursoResponseDto obtenerCursoPorId(Integer id);
    
    List<CursoResponseDto> obtenerTodosLosCursos();
    
    List<CursoResponseDto> obtenerCursosPorEstado(String estado);
    
    List<CursoResponseDto> obtenerCursosPorCategoria(String categoria);
    
    List<CursoResponseDto> obtenerCursosPorNivel(String nivel);
    
    List<CursoResponseDto> buscarCursosPorTitulo(String titulo);
    
    List<CursoResponseDto> obtenerCursosPorCategoriaYNivel(String categoria, String nivel);
    
    CursoResponseDto actualizarCurso(Integer id, CursoRequestDto cursoRequestDto);
    
    void eliminarCurso(Integer id);
}