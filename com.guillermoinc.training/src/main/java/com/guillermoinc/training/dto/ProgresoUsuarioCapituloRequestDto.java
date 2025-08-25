package com.guillermoinc.training.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoUsuarioCapituloRequestDto {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer idUsuario;
    
    @NotNull(message = "El ID del capítulo es obligatorio")
    private Integer idCapitulo;
    
    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    private String estado;
}