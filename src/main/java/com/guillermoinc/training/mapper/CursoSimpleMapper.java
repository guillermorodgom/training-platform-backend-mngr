package com.guillermoinc.training.mapper;

import com.guillermoinc.training.entity.Curso;
import com.guillermoinc.training.dto.CursoSimpleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CursoSimpleMapper {
    
    CursoSimpleDto toDto(Curso curso);
    
    List<CursoSimpleDto> toDto(List<Curso> cursos);
}