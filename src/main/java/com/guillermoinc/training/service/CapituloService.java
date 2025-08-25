package com.guillermoinc.training.service;

import com.guillermoinc.training.dto.CapituloRequestDto;
import com.guillermoinc.training.dto.CapituloResponseDto;

import java.util.List;

public interface CapituloService {
    
    CapituloResponseDto crearCapitulo(CapituloRequestDto capituloRequestDto);
    
    CapituloResponseDto obtenerCapituloPorId(Integer id);
    
    List<CapituloResponseDto> obtenerTodosLosCapitulos();
    
    List<CapituloResponseDto> obtenerCapitulosPorCurso(Integer idCurso);
    
    List<CapituloResponseDto> obtenerCapitulosPorCursoOrdenados(Integer idCurso);
    
    List<CapituloResponseDto> buscarCapitulosPorTitulo(String titulo);
    
    Long contarCapitulosPorCurso(Integer idCurso);
    
    CapituloResponseDto actualizarCapitulo(Integer id, CapituloRequestDto capituloRequestDto);
    
    void eliminarCapitulo(Integer id);
}