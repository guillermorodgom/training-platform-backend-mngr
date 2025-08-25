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
public class CapituloSimpleDto {
    
    private Integer idCapitulo;
    private String titulo;
    private String contenidoUrl;
    private Integer orden;
    private LocalDateTime fechaCreacion;
}