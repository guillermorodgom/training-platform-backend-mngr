package com.guillermoinc.training.service.impl;

import com.guillermoinc.training.dto.CursoRequestDto;
import com.guillermoinc.training.dto.CursoResponseDto;
import com.guillermoinc.training.entity.Curso;
import com.guillermoinc.training.mapper.CursoMapper;
import com.guillermoinc.training.repository.CursoRepository;
import com.guillermoinc.training.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CursoServiceImpl implements CursoService {
    
    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;
    
    @Override
    public CursoResponseDto crearCurso(CursoRequestDto cursoRequestDto) {
        Curso curso = cursoMapper.toEntity(cursoRequestDto);
        Curso cursoGuardado = cursoRepository.save(curso);
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
    public void eliminarCurso(Integer id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado con ID: " + id);
        }
        cursoRepository.deleteById(id);
    }
}