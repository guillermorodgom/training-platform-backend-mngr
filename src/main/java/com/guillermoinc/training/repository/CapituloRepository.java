package com.guillermoinc.training.repository;

import com.guillermoinc.training.entity.Capitulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapituloRepository extends JpaRepository<Capitulo, Integer> {
    
    List<Capitulo> findByIdCurso(Integer idCurso);
    
    @Query("SELECT c FROM Capitulo c WHERE c.idCurso = :idCurso ORDER BY c.orden ASC")
    List<Capitulo> findByIdCursoOrderByOrden(@Param("idCurso") Integer idCurso);
    
    @Query("SELECT c FROM Capitulo c WHERE c.titulo LIKE %:titulo%")
    List<Capitulo> findByTituloContaining(@Param("titulo") String titulo);
    
    @Query("SELECT COUNT(c) FROM Capitulo c WHERE c.idCurso = :idCurso")
    Long countByIdCurso(@Param("idCurso") Integer idCurso);
}