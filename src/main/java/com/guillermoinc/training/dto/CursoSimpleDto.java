package com.guillermoinc.training.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoSimpleDto {
    
    private Integer idCurso;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String nivel;
    private String estado;
    private LocalDateTime fechaCreacion;
}