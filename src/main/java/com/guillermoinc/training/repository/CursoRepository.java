package com.guillermoinc.training.repository;

import com.guillermoinc.training.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    
    List<Curso> findByEstado(String estado);
    
    List<Curso> findByCategoria(String categoria);
    
    List<Curso> findByNivel(String nivel);
    
    @Query("SELECT c FROM Curso c WHERE c.titulo LIKE %:titulo%")
    List<Curso> findByTituloContaining(@Param("titulo") String titulo);
    
    @Query("SELECT c FROM Curso c WHERE c.categoria = :categoria AND c.nivel = :nivel")
    List<Curso> findByCategoriaAndNivel(@Param("categoria") String categoria, @Param("nivel") String nivel);
}