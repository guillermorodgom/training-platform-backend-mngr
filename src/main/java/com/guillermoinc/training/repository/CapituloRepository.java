package com.guillermoinc.training.repository;

import com.guillermoinc.training.entity.Capitulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapituloRepository extends JpaRepository<Capitulo, Integer> {
    
    @Query("SELECT c FROM Capitulo c JOIN c.cursos cur WHERE cur.idCurso = :idCurso")
    List<Capitulo> findByIdCurso(@Param("idCurso") Integer idCurso);
    
    @Query("SELECT c FROM Capitulo c JOIN c.cursos cur WHERE cur.idCurso = :idCurso ORDER BY c.orden ASC")
    List<Capitulo> findByIdCursoOrderByOrden(@Param("idCurso") Integer idCurso);
    
    @Query("SELECT c FROM Capitulo c WHERE c.titulo LIKE %:titulo%")
    List<Capitulo> findByTituloContaining(@Param("titulo") String titulo);
    
    @Query("SELECT COUNT(c) FROM Capitulo c JOIN c.cursos cur WHERE cur.idCurso = :idCurso")
    Long countByIdCurso(@Param("idCurso") Integer idCurso);
    
    @Query("SELECT c FROM Capitulo c WHERE c.cursos IS EMPTY")
    List<Capitulo> findCapitulosIndependientes();
    
    List<Capitulo> findAllByOrderByOrdenAsc();
}