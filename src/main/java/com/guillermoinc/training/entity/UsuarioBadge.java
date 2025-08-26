package com.guillermoinc.training.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "UsuarioBadge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UsuarioBadgeId.class)
public class UsuarioBadge {
    
    @Id
    @Column(name = "id_usuario")
    private Long idUsuario;
    
    @Id
    @Column(name = "id_badge")
    private Long idBadge;
    
    @Column(name = "fecha_obtencion")
    private LocalDateTime fechaObtencion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_badge", referencedColumnName = "id_badge", insertable = false, updatable = false)
    private Badge badge;
    
    @PrePersist
    protected void onCreate() {
        fechaObtencion = LocalDateTime.now();
    }
}