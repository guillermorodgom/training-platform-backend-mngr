package com.guillermoinc.training.service.impl;

import com.guillermoinc.training.client.UserServiceClient;
import com.guillermoinc.training.dto.CapituloConCursoResponseDto;
import com.guillermoinc.training.dto.CursoResponseDto;
import com.guillermoinc.training.dto.CursoSimpleDto;
import com.guillermoinc.training.dto.UsuarioResponseDto;
import com.guillermoinc.training.entity.Capitulo;
import com.guillermoinc.training.entity.Curso;
import com.guillermoinc.training.entity.ProgresoUsuarioCurso;
import com.guillermoinc.training.mapper.CursoMapper;
import com.guillermoinc.training.repository.ProgresoUsuarioCursoRepository;
import com.guillermoinc.training.service.UsuarioCursoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioCursoServiceImpl implements UsuarioCursoService {
    
    private final UserServiceClient userServiceClient;
    private final ProgresoUsuarioCursoRepository progresoUsuarioCursoRepository;
    private final CursoMapper cursoMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDto> obtenerCursosDeUsuario(Integer userId, String authorizationToken) {
        log.info("Obteniendo cursos para usuario ID: {}", userId);
        
        // Verificar que el usuario existe en el microservicio de usuarios
        UsuarioResponseDto usuario = obtenerUsuarioPorId(userId, authorizationToken);
        
        // Obtener todos los cursos en los que el usuario tiene progreso
        List<ProgresoUsuarioCurso> progresosUsuario = progresoUsuarioCursoRepository.findByIdUsuario(userId);
        
        // Convertir a DTOs de cursos
        List<CursoResponseDto> cursosUsuario = progresosUsuario.stream()
                .map(progreso -> {
                    CursoResponseDto cursoDto = cursoMapper.toDto(progreso.getCurso());
                    // Agregar información del progreso
                    cursoDto.setPorcentajeProgreso(progreso.getPorcentaje());
                    cursoDto.setEstadoProgreso(progreso.getEstado());
                    return cursoDto;
                })
                .collect(Collectors.toList());
        
        log.info("Se encontraron {} cursos para el usuario ID: {}", cursosUsuario.size(), userId);
        return cursosUsuario;
    }
    
    @Override
    public UsuarioResponseDto obtenerUsuarioPorId(Integer userId, String authorizationToken) {
        log.info("Consultando usuario ID: {} en microservicio de usuarios", userId);
        
        try {
            UsuarioResponseDto usuario = userServiceClient.getUserById(userId, authorizationToken);
            log.info("Usuario encontrado: {} {}", usuario.getNombre(), usuario.getRol());
            return usuario;
        } catch (Exception e) {
            log.error("Error al consultar usuario ID: {} - Error: {}", userId, e.getMessage());
            throw new RuntimeException("No se pudo obtener información del usuario con ID: " + userId, e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CapituloConCursoResponseDto> obtenerCapitulosDeUsuario(Integer userId, String authorizationToken) {
        log.info("Obteniendo capítulos para usuario ID: {}", userId);
        
        // 1. Verificar que el usuario existe en el microservicio de usuarios
        obtenerUsuarioPorId(userId, authorizationToken);
        
        // 2. PASO CRÍTICO: Consultar tabla ProgresoUsuarioCurso para obtener cursos del usuario
        log.debug("Consultando tabla ProgresoUsuarioCurso para usuario ID: {}", userId);
        List<ProgresoUsuarioCurso> progresosUsuario = progresoUsuarioCursoRepository.findByIdUsuario(userId);
        
        if (progresosUsuario.isEmpty()) {
            log.info("Usuario ID: {} no tiene cursos registrados en ProgresoUsuarioCurso", userId);
            return new ArrayList<>();
        }
        
        log.info("Usuario ID: {} tiene {} cursos en progreso", userId, progresosUsuario.size());
        
        // 3. Extraer los IDs de los cursos desde ProgresoUsuarioCurso
        List<Integer> idsCursosUsuario = progresosUsuario.stream()
                .map(ProgresoUsuarioCurso::getIdCurso)
                .distinct()
                .collect(Collectors.toList());
        
        log.debug("IDs de cursos del usuario: {}", idsCursosUsuario);
        
        // 4. Obtener las entidades Curso completas (con relación a capítulos)
        List<Curso> cursosUsuario = progresosUsuario.stream()
                .map(ProgresoUsuarioCurso::getCurso)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        
        log.debug("Cursos obtenidos: {}", cursosUsuario.stream()
                .map(curso -> curso.getIdCurso() + ":" + curso.getTitulo())
                .collect(Collectors.toList()));
        
        // 5. Procesar capítulos de los cursos usando la relación CursoCapitulo (Many-to-Many)
        Map<Integer, CapituloConCursoResponseDto> capitulosMap = new HashMap<>();
        
        for (Curso curso : cursosUsuario) {
            log.debug("Procesando curso ID: {} - Título: {}", curso.getIdCurso(), curso.getTitulo());
            
            if (curso.getCapitulos() != null && !curso.getCapitulos().isEmpty()) {
                log.debug("Curso {} tiene {} capítulos", curso.getIdCurso(), curso.getCapitulos().size());
                
                for (Capitulo capitulo : curso.getCapitulos()) {
                    // Si el capítulo ya existe en el mapa, agregar este curso a su lista
                    if (capitulosMap.containsKey(capitulo.getIdCapitulo())) {
                        CapituloConCursoResponseDto capituloDto = capitulosMap.get(capitulo.getIdCapitulo());
                        
                        // Verificar que el curso no esté ya agregado
                        boolean cursoYaAgregado = capituloDto.getCursos().stream()
                                .anyMatch(c -> c.getIdCurso().equals(curso.getIdCurso()));
                        
                        if (!cursoYaAgregado) {
                            capituloDto.getCursos().add(CursoSimpleDto.builder()
                                    .idCurso(curso.getIdCurso())
                                    .titulo(curso.getTitulo())
                                    .categoria(curso.getCategoria())
                                    .nivel(curso.getNivel())
                                    .build());
                            log.debug("Agregado curso {} al capítulo existente {}", curso.getIdCurso(), capitulo.getIdCapitulo());
                        }
                    } else {
                        // Crear nuevo capítulo con su primer curso
                        List<CursoSimpleDto> cursosDelCapitulo = new ArrayList<>();
                        cursosDelCapitulo.add(CursoSimpleDto.builder()
                                .idCurso(curso.getIdCurso())
                                .titulo(curso.getTitulo())
                                .categoria(curso.getCategoria())
                                .nivel(curso.getNivel())
                                .build());
                        
                        capitulosMap.put(capitulo.getIdCapitulo(), CapituloConCursoResponseDto.builder()
                                .idCapitulo(capitulo.getIdCapitulo())
                                .titulo(capitulo.getTitulo())
                                .contenidoUrl(capitulo.getContenidoUrl())
                                .orden(capitulo.getOrden())
                                .fechaCreacion(capitulo.getFechaCreacion())
                                .cursos(cursosDelCapitulo)
                                .build());
                        
                        log.debug("Creado nuevo capítulo {} con curso {}", capitulo.getIdCapitulo(), curso.getIdCurso());
                    }
                }
            } else {
                log.debug("Curso {} no tiene capítulos asociados", curso.getIdCurso());
            }
        }
        
        // 6. Convertir a lista y ordenar por orden del capítulo
        List<CapituloConCursoResponseDto> capitulosUsuario = capitulosMap.values().stream()
                .sorted(Comparator.comparing(CapituloConCursoResponseDto::getOrden))
                .collect(Collectors.toList());
        
        log.info("✅ Se encontraron {} capítulos únicos para el usuario ID: {} de {} cursos", 
                capitulosUsuario.size(), userId, cursosUsuario.size());
        
        return capitulosUsuario;
    }
}