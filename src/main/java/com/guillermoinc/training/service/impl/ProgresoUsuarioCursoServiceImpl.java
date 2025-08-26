package com.guillermoinc.training.service.impl;

import com.guillermoinc.training.dto.ProgresoUsuarioCursoRequestDto;
import com.guillermoinc.training.dto.ProgresoUsuarioCursoResponseDto;
import com.guillermoinc.training.entity.ProgresoUsuarioCurso;
import com.guillermoinc.training.entity.Curso;
import com.guillermoinc.training.mapper.ProgresoUsuarioCursoMapper;
import com.guillermoinc.training.repository.ProgresoUsuarioCursoRepository;
import com.guillermoinc.training.repository.CursoRepository;
import com.guillermoinc.training.service.ProgresoUsuarioCursoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProgresoUsuarioCursoServiceImpl implements ProgresoUsuarioCursoService {

    private final ProgresoUsuarioCursoRepository progresoRepository;
    private final CursoRepository cursoRepository;
    private final ProgresoUsuarioCursoMapper progresoMapper;

    @Override
    public ProgresoUsuarioCursoResponseDto registrarProgreso(Integer idUsuario, Integer idCurso, BigDecimal porcentaje) {
        log.info("🎯 Registrando progreso - Usuario: {}, Curso: {}, Porcentaje: {}%", 
                idUsuario, idCurso, porcentaje);

        // Validar que el curso existe
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado con ID: " + idCurso));

        // Verificar si ya existe progreso para este usuario y curso
        Optional<ProgresoUsuarioCurso> progresoExistente = 
                progresoRepository.findByIdUsuarioAndIdCurso(idUsuario, idCurso);

        if (progresoExistente.isPresent()) {
            log.info("📝 Actualizando progreso existente para Usuario: {} en Curso: {}", idUsuario, idCurso);
            return actualizarProgreso(idUsuario, idCurso, porcentaje);
        }

        // Crear nuevo progreso
        ProgresoUsuarioCurso nuevoProgreso = ProgresoUsuarioCurso.builder()
                .idUsuario(idUsuario)
                .idCurso(idCurso)
                .porcentaje(porcentaje)
                .estado(determinarEstado(porcentaje))
                .fechaInicio(LocalDateTime.now())
                .fechaFin(null)
                .curso(curso)
                .build();

        ProgresoUsuarioCurso progresoGuardado = progresoRepository.save(nuevoProgreso);
        
        log.info("✅ Progreso registrado exitosamente - ID Usuario: {}, ID Curso: {}, Estado: {}", 
                idUsuario, idCurso, progresoGuardado.getEstado());

        return progresoMapper.toDto(progresoGuardado);
    }

    @Override
    public ProgresoUsuarioCursoResponseDto actualizarProgreso(Integer idUsuario, Integer idCurso, BigDecimal porcentaje) {
        log.info("🔄 Actualizando progreso - Usuario: {}, Curso: {}, Nuevo Porcentaje: {}%", 
                idUsuario, idCurso, porcentaje);

        ProgresoUsuarioCurso progreso = progresoRepository.findByIdUsuarioAndIdCurso(idUsuario, idCurso)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe progreso para Usuario: " + idUsuario + " en Curso: " + idCurso));

        // Actualizar porcentaje y estado
        progreso.setPorcentaje(porcentaje);
        String nuevoEstado = determinarEstado(porcentaje);
        
        // Si se completa el curso, establecer fecha de finalización
        if ("completado".equals(nuevoEstado) && !"completado".equals(progreso.getEstado())) {
            progreso.setFechaFin(LocalDateTime.now());
            log.info("🎉 Curso completado por Usuario: {} - Curso: {}", idUsuario, idCurso);
        } else if (!"completado".equals(nuevoEstado)) {
            progreso.setFechaFin(null); // Limpiar fecha fin si ya no está completado
        }
        
        progreso.setEstado(nuevoEstado);

        ProgresoUsuarioCurso progresoActualizado = progresoRepository.save(progreso);
        
        log.info("✅ Progreso actualizado - Usuario: {}, Curso: {}, Estado: {}, Porcentaje: {}%", 
                idUsuario, idCurso, nuevoEstado, porcentaje);

        return progresoMapper.toDto(progresoActualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgresoUsuarioCursoResponseDto obtenerProgreso(Integer idUsuario, Integer idCurso) {
        log.debug("🔍 Buscando progreso - Usuario: {}, Curso: {}", idUsuario, idCurso);
        
        return progresoRepository.findByIdUsuarioAndIdCurso(idUsuario, idCurso)
                .map(progresoMapper::toDto)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgresoUsuarioCursoResponseDto> obtenerProgresosPorUsuario(Integer idUsuario) {
        log.debug("📋 Obteniendo todos los progresos del Usuario: {}", idUsuario);
        
        List<ProgresoUsuarioCurso> progresos = progresoRepository.findByIdUsuario(idUsuario);
        
        log.info("📊 Usuario {} tiene {} cursos en progreso", idUsuario, progresos.size());
        
        return progresoMapper.toDto(progresos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgresoUsuarioCursoResponseDto> obtenerProgresosPorCurso(Integer idCurso) {
        log.debug("📋 Obteniendo todos los progresos del Curso: {}", idCurso);
        
        List<ProgresoUsuarioCurso> progresos = progresoRepository.findByIdCurso(idCurso);
        
        log.info("📊 Curso {} tiene {} estudiantes inscritos", idCurso, progresos.size());
        
        return progresoMapper.toDto(progresos);
    }

    @Override
    public ProgresoUsuarioCursoResponseDto completarCurso(Integer idUsuario, Integer idCurso) {
        log.info("🏁 Completando curso - Usuario: {}, Curso: {}", idUsuario, idCurso);
        
        return actualizarProgreso(idUsuario, idCurso, new BigDecimal("100.00"));
    }

    @Override
    public ProgresoUsuarioCursoResponseDto crearOActualizarProgreso(ProgresoUsuarioCursoRequestDto progresoDto) {
        log.info("🔄 Procesando progreso via DTO - Usuario: {}, Curso: {}", 
                progresoDto.getIdUsuario(), progresoDto.getIdCurso());
        
        return registrarProgreso(
                progresoDto.getIdUsuario(), 
                progresoDto.getIdCurso(), 
                progresoDto.getPorcentaje() != null ? progresoDto.getPorcentaje() : BigDecimal.ZERO
        );
    }

    /**
     * Determina el estado basado en el porcentaje de progreso
     * @param porcentaje Porcentaje de progreso
     * @return Estado correspondiente
     */
    private String determinarEstado(BigDecimal porcentaje) {
        if (porcentaje == null) {
            return "pendiente";
        }
        
        if (porcentaje.compareTo(BigDecimal.ZERO) == 0) {
            return "pendiente";
        } else if (porcentaje.compareTo(new BigDecimal("100")) >= 0) {
            return "completado";
        } else {
            return "en_curso";
        }
    }
}