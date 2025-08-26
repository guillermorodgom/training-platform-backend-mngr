package com.guillermoinc.training.service.impl;

import com.guillermoinc.training.dto.CursoRequestDto;
import com.guillermoinc.training.dto.CursoResponseDto;
import com.guillermoinc.training.entity.Capitulo;
import com.guillermoinc.training.entity.Curso;
import com.guillermoinc.training.mapper.CursoMapper;
import com.guillermoinc.training.repository.CapituloRepository;
import com.guillermoinc.training.repository.CursoRepository;
import com.guillermoinc.training.service.CursoService;
import com.guillermoinc.training.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CursoServiceImpl implements CursoService {
    
    private final CursoRepository cursoRepository;
    private final CapituloRepository capituloRepository;
    private final CursoMapper cursoMapper;
    private final EmailService emailService;
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange cursosExchange;
    
    @Override
    public CursoResponseDto crearCurso(CursoRequestDto cursoRequestDto) {
        System.out.println("Creando curso: " + cursoRequestDto);
        Curso curso = cursoMapper.toEntity(cursoRequestDto);
        Curso cursoGuardado = cursoRepository.save(curso);
        publicarEventoCursoCreado(cursoGuardado);
        
        log.info("Enviando notificación: Nuevo curso creado - ID: {}, Título: '{}'", 
                cursoGuardado.getIdCurso(), cursoGuardado.getTitulo());
        
        // Enviar notificación por email
        // try {
        //     emailService.enviarNotificacionNuevoCurso(cursoGuardado);
        // } catch (Exception e) {
        //     log.warn("No se pudo enviar notificación por email para el curso: {} - Error: {}", 
        //             cursoGuardado.getTitulo(), e.getMessage());
        // }
        
        return cursoMapper.toDto(cursoGuardado);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CursoResponseDto obtenerCursoPorId(Integer id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
        return cursoMapper.toDto(curso);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDto> obtenerTodosLosCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursoMapper.toDto(cursos);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDto> obtenerCursosPorEstado(String estado) {
        List<Curso> cursos = cursoRepository.findByEstado(estado);
        return cursoMapper.toDto(cursos);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDto> obtenerCursosPorCategoria(String categoria) {
        List<Curso> cursos = cursoRepository.findByCategoria(categoria);
        return cursoMapper.toDto(cursos);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDto> obtenerCursosPorNivel(String nivel) {
        List<Curso> cursos = cursoRepository.findByNivel(nivel);
        return cursoMapper.toDto(cursos);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDto> buscarCursosPorTitulo(String titulo) {
        List<Curso> cursos = cursoRepository.findByTituloContaining(titulo);
        return cursoMapper.toDto(cursos);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDto> obtenerCursosPorCategoriaYNivel(String categoria, String nivel) {
        List<Curso> cursos = cursoRepository.findByCategoriaAndNivel(categoria, nivel);
        return cursoMapper.toDto(cursos);
    }
    
    @Override
    public CursoResponseDto actualizarCurso(Integer id, CursoRequestDto cursoRequestDto) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
        
        cursoMapper.updateEntityFromDto(cursoRequestDto, curso);
        Curso cursoActualizado = cursoRepository.save(curso);
        return cursoMapper.toDto(cursoActualizado);
    }
    
    @Override
    public CursoResponseDto asociarCursoConCapitulos(Integer idCurso, List<Integer> idsCapitulos) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + idCurso));
        
        List<Capitulo> capitulosExistentes = curso.getCapitulos();
        if (capitulosExistentes == null) {
            capitulosExistentes = new ArrayList<>();
        }
        
        for (Integer idCapitulo : idsCapitulos) {
            Capitulo capitulo = capituloRepository.findById(idCapitulo)
                    .orElseThrow(() -> new RuntimeException("Capítulo no encontrado con ID: " + idCapitulo));
            if (!capitulosExistentes.contains(capitulo)) {
                capitulosExistentes.add(capitulo);
            }
        }
        
        curso.setCapitulos(capitulosExistentes);
        Curso cursoActualizado = cursoRepository.save(curso);
        return cursoMapper.toDto(cursoActualizado);
    }
    
    @Override
    public CursoResponseDto desasociarCursoDeCapitulo(Integer idCurso, Integer idCapitulo) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + idCurso));
        
        Capitulo capitulo = capituloRepository.findById(idCapitulo)
                .orElseThrow(() -> new RuntimeException("Capítulo no encontrado con ID: " + idCapitulo));
        
        List<Capitulo> capitulos = curso.getCapitulos();
        if (capitulos != null) {
            capitulos.remove(capitulo);
            curso.setCapitulos(capitulos);
        }
        
        Curso cursoActualizado = cursoRepository.save(curso);
        return cursoMapper.toDto(cursoActualizado);
    }
    
    @Override
    public void eliminarCurso(Integer id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado con ID: " + id);
        }
        cursoRepository.deleteById(id);
    }

    public void publicarEventoCursoCreado(Curso curso) {
        System.out.println("Publicando evento: curso creado -> " + curso);
        String routingKey = "curso.creado";

        // Enviamos el mensaje al exchange declarado en la configuración
        rabbitTemplate.convertAndSend(cursosExchange.getName(), routingKey, curso);

        System.out.println("Evento publicado: curso creado -> " + curso);
    }
}