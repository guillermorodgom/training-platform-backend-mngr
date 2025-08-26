package com.guillermoinc.training.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "progresousuariocurso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ProgresoUsuarioCursoId.class)
public class ProgresoUsuarioCurso {
    
    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    @Id
    @Column(name = "id_curso")
    private Integer idCurso;
    
    @Column(length = 20)
    @Builder.Default
    private String estado = "pendiente";
    
    @Column(precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal porcentaje = BigDecimal.ZERO;
    
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false)
    private Curso curso;
    
    @PrePersist
    protected void onCreate() {
        fechaInicio = LocalDateTime.now();
    }
}