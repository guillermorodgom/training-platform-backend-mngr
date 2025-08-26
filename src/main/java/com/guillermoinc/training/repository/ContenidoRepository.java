package com.guillermoinc.training.repository;

import com.guillermoinc.training.entity.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Integer> {
    
    List<Contenido> findByTipoExtension(String tipoExtension);
    
    List<Contenido> findByTipoExtensionIgnoreCase(String tipoExtension);
    
    List<Contenido> findByNombreArchivoContaining(String nombreArchivo);
    
    List<Contenido> findByCursoIdCurso(Integer idCurso);
    
    @Query("SELECT c FROM Contenido c WHERE c.tamaño BETWEEN :minSize AND :maxSize")
    List<Contenido> findByTamañoBetween(@Param("minSize") Long minSize, @Param("maxSize") Long maxSize);
    
    @Query("SELECT c FROM Contenido c ORDER BY c.fechaCreacion DESC")
    List<Contenido> findAllOrderByFechaCreacionDesc();
}