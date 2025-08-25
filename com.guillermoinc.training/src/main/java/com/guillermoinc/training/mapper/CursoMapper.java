package com.guillermoinc.training.mapper;

import com.guillermoinc.training.entity.Curso;
import com.guillermoinc.training.dto.CursoRequestDto;
import com.guillermoinc.training.dto.CursoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CapituloMapper.class})
public interface CursoMapper {
    
    CursoResponseDto toDto(Curso curso);
    
    List<CursoResponseDto> toDto(List<Curso> cursos);
    
    @Mapping(target = "idCurso", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "capitulos", ignore = true)
    @Mapping(target = "progresoUsuarios", ignore = true)
    Curso toEntity(CursoRequestDto cursoRequestDto);
    
    @Mapping(target = "idCurso", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "capitulos", ignore = true)
    @Mapping(target = "progresoUsuarios", ignore = true)
    void updateEntityFromDto(CursoRequestDto cursoRequestDto, @MappingTarget Curso curso);
}