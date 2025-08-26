package com.guillermoinc.training.mapper;

import com.guillermoinc.training.entity.Contenido;
import com.guillermoinc.training.dto.ContenidoRequestDto;
import com.guillermoinc.training.dto.ContenidoResponseDto;
import com.guillermoinc.training.dto.ContenidoConDatosResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CursoSimpleMapper.class})
public interface ContenidoMapper {
    
    // Mapeo sin datos binarios (para listados y metadata)
    ContenidoResponseDto toDto(Contenido contenido);
    
    List<ContenidoResponseDto> toDto(List<Contenido> contenidos);
    
    // Mapeo completo con datos binarios
    ContenidoConDatosResponseDto toDtoConDatos(Contenido contenido);
    
    // Mapeo de request a entity
    @Mapping(target = "idContenido", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "curso", ignore = true)
    Contenido toEntity(ContenidoRequestDto contenidoRequestDto);
    
    // Actualización de entity desde DTO
    @Mapping(target = "idContenido", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "curso", ignore = true)
    void updateEntityFromDto(ContenidoRequestDto contenidoRequestDto, @MappingTarget Contenido contenido);
}