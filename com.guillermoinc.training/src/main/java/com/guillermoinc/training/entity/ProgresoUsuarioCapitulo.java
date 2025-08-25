package com.guillermoinc.training.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "progresousuariocapitulo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ProgresoUsuarioCapituloId.class)
public class ProgresoUsuarioCapitulo {
    
    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    @Id
    @Column(name = "id_capitulo", insertable = false, updatable = false)
    private Integer idCapitulo;
    
    @Column(length = 20)
    @Builder.Default
    private String estado = "pendiente";
    
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_capitulo", referencedColumnName = "id_capitulo")
    private Capitulo capitulo;
    
    @PrePersist
    protected void onCreate() {
        fechaInicio = LocalDateTime.now();
    }
}