package com.guillermoinc.training.service;

import com.guillermoinc.training.dto.ContenidoResponseDto;
import com.guillermoinc.training.dto.ContenidoConDatosResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContenidoService {
    
    ContenidoResponseDto cargar(MultipartFile archivo, Integer idCurso);
    
    ContenidoResponseDto obtenerContenidoPorId(Integer id);
    
    ContenidoConDatosResponseDto descargarContenido(Integer id);
    
    List<ContenidoResponseDto> obtenerTodosLosContenidos();
    
    List<ContenidoResponseDto> buscarContenidosPorExtension(String extension);
    
    List<ContenidoResponseDto> obtenerContenidosPorCurso(Integer idCurso);
    
    void eliminarContenido(Integer id);
}