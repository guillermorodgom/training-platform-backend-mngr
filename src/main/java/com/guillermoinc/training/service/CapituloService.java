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
    
    List<CapituloResponseDto> obtenerCapitulosIndependientes();
    
    Long contarCapitulosPorCurso(Integer idCurso);
    
    CapituloResponseDto actualizarCapitulo(Integer id, CapituloRequestDto capituloRequestDto);
    
    CapituloResponseDto asociarCapituloConCursos(Integer idCapitulo, List<Integer> idsCursos);
    
    CapituloResponseDto desasociarCapituloDeCurso(Integer idCapitulo, Integer idCurso);
    
    void eliminarCapitulo(Integer id);
}