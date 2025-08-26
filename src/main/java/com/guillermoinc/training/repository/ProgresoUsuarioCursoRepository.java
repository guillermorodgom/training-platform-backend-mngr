package com.guillermoinc.training.repository;

import com.guillermoinc.training.entity.ProgresoUsuarioCurso;
import com.guillermoinc.training.entity.ProgresoUsuarioCursoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgresoUsuarioCursoRepository extends JpaRepository<ProgresoUsuarioCurso, ProgresoUsuarioCursoId> {
    
    List<ProgresoUsuarioCurso> findByIdUsuario(Integer idUsuario);
    
    List<ProgresoUsuarioCurso> findByIdCurso(Integer idCurso);
    
    Optional<ProgresoUsuarioCurso> findByIdUsuarioAndIdCurso(Integer idUsuario, Integer idCurso);
    
    @Query("SELECT p FROM ProgresoUsuarioCurso p WHERE p.idUsuario = :idUsuario AND p.estado = :estado")
    List<ProgresoUsuarioCurso> findByIdUsuarioAndEstado(@Param("idUsuario") Integer idUsuario, @Param("estado") String estado);
    
    @Query("SELECT p FROM ProgresoUsuarioCurso p WHERE p.idCurso = :idCurso AND p.estado = :estado")
    List<ProgresoUsuarioCurso> findByIdCursoAndEstado(@Param("idCurso") Integer idCurso, @Param("estado") String estado);
}