package com.guillermoinc.training.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    @Column(nullable = false, length = 100, name = "nombre")
    private String nombre;
    
    @Column(nullable = false, unique = true, length = 150, name = "correo")
    private String correo;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 50, name = "rol")
    private Role rol;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProgresoUsuarioCurso> progresoUsuarios;
    
    @PrePersist
    protected void onCreate() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
    }
}