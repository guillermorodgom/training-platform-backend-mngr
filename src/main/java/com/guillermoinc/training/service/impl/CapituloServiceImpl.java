package com.guillermoinc.training.service.impl;

import com.guillermoinc.training.dto.CapituloRequestDto;
import com.guillermoinc.training.dto.CapituloResponseDto;
import com.guillermoinc.training.entity.Capitulo;
import com.guillermoinc.training.entity.Curso;
import com.guillermoinc.training.mapper.CapituloMapper;
import com.guillermoinc.training.repository.CapituloRepository;
import com.guillermoinc.training.repository.CursoRepository;
import com.guillermoinc.training.service.CapituloService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class CapituloServiceImpl implements CapituloService {
    
    private final CapituloRepository capituloRepository;
    private final CursoRepository cursoRepository;
    private final CapituloMapper capituloMapper;
    
    @Override
    public CapituloResponseDto crearCapitulo(CapituloRequestDto capituloRequestDto) {
        Capitulo capitulo = capituloMapper.toEntity(capituloRequestDto);
        
        // Asociar con cursos si se proporcionan IDs
        if (capituloRequestDto.getIdsCursos() != null && !capituloRequestDto.getIdsCursos().isEmpty()) {
            List<Curso> cursos = new ArrayList<>();
            for (Integer idCurso : capituloRequestDto.getIdsCursos()) {
                Curso curso = cursoRepository.findById(idCurso)
                        .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + idCurso));
                cursos.add(curso);
            }
            capitulo.setCursos(cursos);
        }
        
        Capitulo capituloGuardado = capituloRepository.save(capitulo);
        return capituloMapper.toDto(capituloGuardado);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CapituloResponseDto obtenerCapituloPorId(Integer id) {
        Capitulo capitulo = capituloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Capítulo no encontrado con ID: " + id));
        return capituloMapper.toDto(capitulo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CapituloResponseDto> obtenerTodosLosCapitulos() {
        List<Capitulo> capitulos = capituloRepository.findAll();
        return capituloMapper.toDto(capitulos);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CapituloResponseDto> obtenerCapitulosPorCurso(Integer idCurso) {
        List<Capitulo> capitulos = capituloRepository.findByIdCurso(idCurso);
        return capituloMapper.toDto(capitulos);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CapituloResponseDto> obtenerCapitulosPorCursoOrdenados(Integer idCurso) {
        List<Capitulo> capitulos = capituloRepository.findByIdCursoOrderByOrden(idCurso);
        return capituloMapper.toDto(capitulos);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CapituloResponseDto> buscarCapitulosPorTitulo(String titulo) {
        List<Capitulo> capitulos = capituloRepository.findByTituloContaining(titulo);
        return capituloMapper.toDto(capitulos);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CapituloResponseDto> obtenerCapitulosIndependientes() {
        List<Capitulo> capitulos = capituloRepository.findCapitulosIndependientes();
        return capituloMapper.toDto(capitulos);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long contarCapitulosPorCurso(Integer idCurso) {
        return capituloRepository.countByIdCurso(idCurso);
    }
    
    @Override
    public CapituloResponseDto actualizarCapitulo(Integer id, CapituloRequestDto capituloRequestDto) {
        Capitulo capitulo = capituloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Capítulo no encontrado con ID: " + id));
        
        // Actualizar relaciones con cursos
        if (capituloRequestDto.getIdsCursos() != null) {
            List<Curso> cursos = new ArrayList<>();
            for (Integer idCurso : capituloRequestDto.getIdsCursos()) {
                Curso curso = cursoRepository.findById(idCurso)
                        .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + idCurso));
                cursos.add(curso);
            }
            capitulo.setCursos(cursos);
        } else {
            // Si no se proporcionan IDs, limpiar todas las asociaciones
            capitulo.setCursos(new ArrayList<>());
        }
        
        capituloMapper.updateEntityFromDto(capituloRequestDto, capitulo);
        Capitulo capituloActualizado = capituloRepository.save(capitulo);
        return capituloMapper.toDto(capituloActualizado);
    }
    
    @Override
    public CapituloResponseDto asociarCapituloConCursos(Integer idCapitulo, List<Integer> idsCursos) {
        Capitulo capitulo = capituloRepository.findById(idCapitulo)
                .orElseThrow(() -> new RuntimeException("Capítulo no encontrado con ID: " + idCapitulo));
        
        List<Curso> cursosExistentes = capitulo.getCursos();
        if (cursosExistentes == null) {
            cursosExistentes = new ArrayList<>();
        }
        
        for (Integer idCurso : idsCursos) {
            Curso curso = cursoRepository.findById(idCurso)
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + idCurso));
            if (!cursosExistentes.contains(curso)) {
                cursosExistentes.add(curso);
            }
        }
        
        capitulo.setCursos(cursosExistentes);
        Capitulo capituloActualizado = capituloRepository.save(capitulo);
        return capituloMapper.toDto(capituloActualizado);
    }
    
    @Override
    public CapituloResponseDto desasociarCapituloDeCurso(Integer idCapitulo, Integer idCurso) {
        Capitulo capitulo = capituloRepository.findById(idCapitulo)
                .orElseThrow(() -> new RuntimeException("Capítulo no encontrado con ID: " + idCapitulo));
        
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + idCurso));
        
        List<Curso> cursos = capitulo.getCursos();
        if (cursos != null) {
            cursos.remove(curso);
            capitulo.setCursos(cursos);
        }
        
        Capitulo capituloActualizado = capituloRepository.save(capitulo);
        return capituloMapper.toDto(capituloActualizado);
    }
    
    @Override
    public void eliminarCapitulo(Integer id) {
        if (!capituloRepository.existsById(id)) {
            throw new RuntimeException("Capítulo no encontrado con ID: " + id);
        }
        capituloRepository.deleteById(id);
    }
}