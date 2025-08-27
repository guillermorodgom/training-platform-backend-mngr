package com.guillermoinc.training.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoResponseDto {
    
    private Integer idCurso;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String nivel;
    private String estado;
    private LocalDateTime fechaCreacion;
    private List<CapituloSimpleDto> capitulos;
    
    // Campos de progreso del usuario (opcionales)
    private BigDecimal porcentajeProgreso;
    private String estadoProgreso;
}