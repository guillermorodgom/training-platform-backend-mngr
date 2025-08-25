package com.guillermoinc.training.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoUsuarioCursoId implements Serializable {
    
    private Integer idUsuario;
    private Integer idCurso;
}