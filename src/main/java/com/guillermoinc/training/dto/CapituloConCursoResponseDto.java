package com.guillermoinc.training.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapituloConCursoResponseDto {
    
    private Integer idCapitulo;
    private String titulo;
    private String contenidoUrl;
    private Integer orden;
    private LocalDateTime fechaCreacion;
    
    // Lista de cursos asociados a este capítulo
    private List<CursoSimpleDto> cursos;
}