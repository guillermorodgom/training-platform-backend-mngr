package com.guillermoinc.training.dto;

import com.guillermoinc.training.entity.Role;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDto {
    
    private Integer id;
    private String nombre;
    private String correo;
    private Role rol;
    private LocalDateTime fechaRegistro;
}