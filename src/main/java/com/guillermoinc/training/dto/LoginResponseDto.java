package com.guillermoinc.training.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private UsuarioResponseDto user;
}