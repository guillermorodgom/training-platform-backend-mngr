package com.guillermoinc.training.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "capitulo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Capitulo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_capitulo")
    private Integer idCapitulo;
    
    @Column(name = "id_curso", nullable = false, insertable = false, updatable = false)
    private Integer idCurso;
    
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @Column(name = "contenido_url", columnDefinition = "TEXT")
    private String contenidoUrl;
    
    @Column(nullable = false)
    private Integer orden;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    private Curso curso;
    
    @OneToMany(mappedBy = "capitulo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProgresoUsuarioCapitulo> progresosUsuario;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}