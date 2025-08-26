package com.guillermoinc.training.mapper;

import com.guillermoinc.training.entity.Capitulo;
import com.guillermoinc.training.dto.CapituloSimpleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CapituloSimpleMapper {
    
    CapituloSimpleDto toDto(Capitulo capitulo);
    
    List<CapituloSimpleDto> toDto(List<Capitulo> capitulos);
}