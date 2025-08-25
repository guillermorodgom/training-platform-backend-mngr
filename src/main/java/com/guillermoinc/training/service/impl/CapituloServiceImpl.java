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

@Service
@RequiredArgsConstructor
@Transactional
public class CapituloServiceImpl implements CapituloService {
    
    private final CapituloRepository capituloRepository;
    private final CursoRepository cursoRepository;
    private final CapituloMapper capituloMapper;
    
    @Override
    public CapituloResponseDto crearCapitulo(CapituloRequestDto capituloRequestDto) {
        Curso curso = cursoRepository.findById(capituloRequestDto.getIdCurso())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + capituloRequestDto.getIdCurso()));
        
        Capitulo capitulo = capituloMapper.toEntity(capituloRequestDto);
        capitulo.setCurso(curso);
        
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
    public Long contarCapitulosPorCurso(Integer idCurso) {
        return capituloRepository.countByIdCurso(idCurso);
    }
    
    @Override
    public CapituloResponseDto actualizarCapitulo(Integer id, CapituloRequestDto capituloRequestDto) {
        Capitulo capitulo = capituloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Capítulo no encontrado con ID: " + id));
        
        if (!capitulo.getIdCurso().equals(capituloRequestDto.getIdCurso())) {
            Curso nuevoCurso = cursoRepository.findById(capituloRequestDto.getIdCurso())
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + capituloRequestDto.getIdCurso()));
            capitulo.setCurso(nuevoCurso);
        }
        
        capituloMapper.updateEntityFromDto(capituloRequestDto, capitulo);
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