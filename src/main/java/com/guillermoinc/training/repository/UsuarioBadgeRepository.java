package com.guillermoinc.training.repository;

import com.guillermoinc.training.entity.UsuarioBadge;
import com.guillermoinc.training.entity.UsuarioBadgeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioBadgeRepository extends JpaRepository<UsuarioBadge, UsuarioBadgeId> {
    
    List<UsuarioBadge> findByIdUsuario(Long idUsuario);
    
    List<UsuarioBadge> findByIdBadge(Long idBadge);
    
    boolean existsByIdUsuarioAndIdBadge(Long idUsuario, Long idBadge);
}