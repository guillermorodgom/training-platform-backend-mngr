package com.guillermoinc.training.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "Badge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Badge {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_badge")
    private Long idBadge;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 50)
    private String icon;
    
    @Column(length = 20)
    private String color;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BadgeCategory category;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private BadgeRarity rarity = BadgeRarity.COMMON;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer points = 0;
    
    @Column(name = "course_id")
    private Integer courseId;
    
    @Column(columnDefinition = "TEXT")
    private String requirements;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}