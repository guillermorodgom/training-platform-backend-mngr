package com.guillermoinc.training.mapper;

import com.guillermoinc.training.entity.Capitulo;
import com.guillermoinc.training.dto.CapituloRequestDto;
import com.guillermoinc.training.dto.CapituloResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CapituloMapper {
    
    @Mapping(target = "cursos", ignore = true)
    CapituloResponseDto toDto(Capitulo capitulo);
    
    List<CapituloResponseDto> toDto(List<Capitulo> capitulos);
    
    @Mapping(target = "idCapitulo", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    @Mapping(target = "progresosUsuario", ignore = true)
    Capitulo toEntity(CapituloRequestDto capituloRequestDto);
    
    @Mapping(target = "idCapitulo", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    @Mapping(target = "progresosUsuario", ignore = true)
    void updateEntityFromDto(CapituloRequestDto capituloRequestDto, @MappingTarget Capitulo capitulo);
}