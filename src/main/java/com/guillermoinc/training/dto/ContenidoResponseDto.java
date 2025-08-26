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
public class ContenidoResponseDto {
    
    private Integer idContenido;
    private String nombreArchivo;
    private String tipoExtension;
    private Long tamaño;
    private LocalDateTime fechaCreacion;
    private CursoSimpleDto curso;
    
    // Nota: Los datos binarios no se incluyen en la respuesta por defecto
    // para evitar transferir grandes cantidades de datos innecesariamente
}