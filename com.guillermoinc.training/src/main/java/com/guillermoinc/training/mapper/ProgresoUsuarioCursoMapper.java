package com.guillermoinc.training.mapper;

import com.guillermoinc.training.entity.ProgresoUsuarioCurso;
import com.guillermoinc.training.dto.ProgresoUsuarioCursoRequestDto;
import com.guillermoinc.training.dto.ProgresoUsuarioCursoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CursoMapper.class})
public interface ProgresoUsuarioCursoMapper {
    
    ProgresoUsuarioCursoResponseDto toDto(ProgresoUsuarioCurso progresoUsuarioCurso);
    
    List<ProgresoUsuarioCursoResponseDto> toDto(List<ProgresoUsuarioCurso> progresosUsuarioCurso);
    
    @Mapping(target = "fechaInicio", ignore = true)
    @Mapping(target = "fechaFin", ignore = true)
    @Mapping(target = "curso", ignore = true)
    ProgresoUsuarioCurso toEntity(ProgresoUsuarioCursoRequestDto progresoUsuarioCursoRequestDto);
    
    @Mapping(target = "fechaInicio", ignore = true)
    @Mapping(target = "fechaFin", ignore = true)
    @Mapping(target = "curso", ignore = true)
    void updateEntityFromDto(ProgresoUsuarioCursoRequestDto progresoUsuarioCursoRequestDto, @MappingTarget ProgresoUsuarioCurso progresoUsuarioCurso);
}