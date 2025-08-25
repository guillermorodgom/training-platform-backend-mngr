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
public class ProgresoUsuarioCapituloResponseDto {
    
    private Integer idUsuario;
    private Integer idCapitulo;
    private String estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private CapituloResponseDto capitulo;
}