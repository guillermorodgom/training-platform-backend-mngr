package com.guillermoinc.training.service;

import com.guillermoinc.training.dto.ProgresoUsuarioCursoRequestDto;
import com.guillermoinc.training.dto.ProgresoUsuarioCursoResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProgresoUsuarioCursoService {
    
    /**
     * Registra el progreso de un usuario en un curso
     * @param idUsuario ID del usuario
     * @param idCurso ID del curso  
     * @param porcentaje Porcentaje de progreso (0-100)
     * @return ProgresoUsuarioCursoResponseDto con el progreso registrado
     */
    ProgresoUsuarioCursoResponseDto registrarProgreso(Integer idUsuario, Integer idCurso, BigDecimal porcentaje);
    
    /**
     * Actualiza el progreso existente de un usuario en un curso
     * @param idUsuario ID del usuario
     * @param idCurso ID del curso
     * @param porcentaje Nuevo porcentaje de progreso
     * @return ProgresoUsuarioCursoResponseDto actualizado
     */
    ProgresoUsuarioCursoResponseDto actualizarProgreso(Integer idUsuario, Integer idCurso, BigDecimal porcentaje);
    
    /**
     * Obtiene el progreso de un usuario en un curso específico
     * @param idUsuario ID del usuario
     * @param idCurso ID del curso
     * @return ProgresoUsuarioCursoResponseDto o null si no existe
     */
    ProgresoUsuarioCursoResponseDto obtenerProgreso(Integer idUsuario, Integer idCurso);
    
    /**
     * Obtiene todos los progresos de un usuario
     * @param idUsuario ID del usuario
     * @return Lista de ProgresoUsuarioCursoResponseDto
     */
    List<ProgresoUsuarioCursoResponseDto> obtenerProgresosPorUsuario(Integer idUsuario);
    
    /**
     * Obtiene todos los progresos de un curso
     * @param idCurso ID del curso
     * @return Lista de ProgresoUsuarioCursoResponseDto
     */
    List<ProgresoUsuarioCursoResponseDto> obtenerProgresosPorCurso(Integer idCurso);
    
    /**
     * Marca un curso como completado para un usuario
     * @param idUsuario ID del usuario
     * @param idCurso ID del curso
     * @return ProgresoUsuarioCursoResponseDto actualizado
     */
    ProgresoUsuarioCursoResponseDto completarCurso(Integer idUsuario, Integer idCurso);
    
    /**
     * Crea o actualiza progreso usando un DTO
     * @param progresoDto DTO con los datos del progreso
     * @return ProgresoUsuarioCursoResponseDto
     */
    ProgresoUsuarioCursoResponseDto crearOActualizarProgreso(ProgresoUsuarioCursoRequestDto progresoDto);
}