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
public class ContenidoConDatosResponseDto {
    
    private Integer idContenido;
    private String nombreArchivo;
    private String tipoExtension;
    private Long tamaño;
    private byte[] datos;
    private LocalDateTime fechaCreacion;
    private CursoSimpleDto curso;
    
    // DTO específico para cuando se necesitan los datos binarios completos
}