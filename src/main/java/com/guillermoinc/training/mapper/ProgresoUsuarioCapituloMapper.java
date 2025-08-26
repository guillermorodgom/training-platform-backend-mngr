package com.guillermoinc.training.mapper;

import com.guillermoinc.training.entity.ProgresoUsuarioCapitulo;
import com.guillermoinc.training.dto.ProgresoUsuarioCapituloRequestDto;
import com.guillermoinc.training.dto.ProgresoUsuarioCapituloResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProgresoUsuarioCapituloMapper {
    
    @Mapping(target = "capitulo", ignore = true)
    ProgresoUsuarioCapituloResponseDto toDto(ProgresoUsuarioCapitulo progresoUsuarioCapitulo);
    
    List<ProgresoUsuarioCapituloResponseDto> toDto(List<ProgresoUsuarioCapitulo> progresosUsuarioCapitulo);
    
    @Mapping(target = "fechaInicio", ignore = true)
    @Mapping(target = "fechaFin", ignore = true)
    @Mapping(target = "capitulo", ignore = true)
    ProgresoUsuarioCapitulo toEntity(ProgresoUsuarioCapituloRequestDto progresoUsuarioCapituloRequestDto);
    
    @Mapping(target = "fechaInicio", ignore = true)
    @Mapping(target = "fechaFin", ignore = true)
    @Mapping(target = "capitulo", ignore = true)
    void updateEntityFromDto(ProgresoUsuarioCapituloRequestDto progresoUsuarioCapituloRequestDto, @MappingTarget ProgresoUsuarioCapitulo progresoUsuarioCapitulo);
}