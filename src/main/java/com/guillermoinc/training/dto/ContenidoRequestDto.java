package com.guillermoinc.training.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContenidoRequestDto {
    
    @NotBlank(message = "El nombre del archivo es obligatorio")
    @Size(max = 255, message = "El nombre del archivo no puede exceder 255 caracteres")
    private String nombreArchivo;
    
    @NotBlank(message = "El tipo de extensión es obligatorio")
    @Size(max = 10, message = "La extensión no puede exceder 10 caracteres")
    private String tipoExtension;
    
    @NotNull(message = "El tamaño del archivo es obligatorio")
    @Positive(message = "El tamaño del archivo debe ser positivo")
    private Long tamaño;
    
    @NotNull(message = "Los datos del archivo son obligatorios")
    private byte[] datos;
    
    @NotNull(message = "El ID del curso es obligatorio")
    @Positive(message = "El ID del curso debe ser positivo")
    private Integer idCurso;
}