package com.guillermoinc.training.service.impl;

import com.guillermoinc.training.dto.ContenidoResponseDto;
import com.guillermoinc.training.dto.ContenidoConDatosResponseDto;
import com.guillermoinc.training.entity.Contenido;
import com.guillermoinc.training.entity.Curso;
import com.guillermoinc.training.mapper.ContenidoMapper;
import com.guillermoinc.training.repository.ContenidoRepository;
import com.guillermoinc.training.repository.CursoRepository;
import com.guillermoinc.training.service.ContenidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContenidoServiceImpl implements ContenidoService {
    
    private final ContenidoRepository contenidoRepository;
    private final CursoRepository cursoRepository;
    private final ContenidoMapper contenidoMapper;
    
    @Override
    public ContenidoResponseDto cargar(MultipartFile archivo, Integer idCurso) {
        try {
            // Validar que el curso existe
            Curso curso = cursoRepository.findById(idCurso)
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + idCurso));
            
            // Obtener la extensión del archivo
            String nombreArchivo = archivo.getOriginalFilename();
            String extension = obtenerExtension(nombreArchivo);
            
            // Imprimir la extensión como se solicita
            log.info("📁 Cargando archivo: '{}' - Extensión detectada: '{}' para curso: '{}'", 
                    nombreArchivo, extension, curso.getTitulo());
            System.out.println("Extensión del archivo: " + extension);
            
            // Crear la entidad Contenido
            Contenido contenido = Contenido.builder()
                    .nombreArchivo(nombreArchivo)
                    .tipoExtension(extension)
                    .tamaño(archivo.getSize())
                    .datos(archivo.getBytes())
                    .curso(curso)
                    .build();
            
            // Guardar en la base de datos
            Contenido contenidoGuardado = contenidoRepository.save(contenido);
            
            log.info("✅ Archivo guardado exitosamente con ID: {} - Tamaño: {} bytes - Curso: {}", 
                    contenidoGuardado.getIdContenido(), contenidoGuardado.getTamaño(), curso.getTitulo());
            
            return contenidoMapper.toDto(contenidoGuardado);
            
        } catch (IOException e) {
            log.error("❌ Error al cargar el archivo: {}", e.getMessage());
            throw new RuntimeException("Error al procesar el archivo: " + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public ContenidoResponseDto obtenerContenidoPorId(Integer id) {
        Contenido contenido = contenidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenido no encontrado con ID: " + id));
        return contenidoMapper.toDto(contenido);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ContenidoConDatosResponseDto descargarContenido(Integer id) {
        Contenido contenido = contenidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenido no encontrado con ID: " + id));
        
        log.info("📥 Descargando archivo: '{}' (ID: {})", contenido.getNombreArchivo(), id);
        return contenidoMapper.toDtoConDatos(contenido);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ContenidoResponseDto> obtenerTodosLosContenidos() {
        List<Contenido> contenidos = contenidoRepository.findAllOrderByFechaCreacionDesc();
        return contenidoMapper.toDto(contenidos);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ContenidoResponseDto> buscarContenidosPorExtension(String extension) {
        List<Contenido> contenidos = contenidoRepository.findByTipoExtensionIgnoreCase(extension);
        log.info("🔍 Encontrados {} archivos con extensión '{}'", contenidos.size(), extension);
        return contenidoMapper.toDto(contenidos);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ContenidoResponseDto> obtenerContenidosPorCurso(Integer idCurso) {
        // Validar que el curso existe
        if (!cursoRepository.existsById(idCurso)) {
            throw new RuntimeException("Curso no encontrado con ID: " + idCurso);
        }
        
        List<Contenido> contenidos = contenidoRepository.findByCursoIdCurso(idCurso);
        log.info("📚 Encontrados {} contenidos para el curso con ID: {}", contenidos.size(), idCurso);
        return contenidoMapper.toDto(contenidos);
    }
    
    @Override
    public void eliminarContenido(Integer id) {
        if (!contenidoRepository.existsById(id)) {
            throw new RuntimeException("Contenido no encontrado con ID: " + id);
        }
        contenidoRepository.deleteById(id);
        log.info("🗑️ Contenido eliminado con ID: {}", id);
    }
    
    /**
     * Método utilitario para extraer la extensión del archivo
     */
    private String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            return "sin_extension";
        }
        
        int ultimoPunto = nombreArchivo.lastIndexOf('.');
        if (ultimoPunto == -1 || ultimoPunto == nombreArchivo.length() - 1) {
            return "sin_extension";
        }
        
        return nombreArchivo.substring(ultimoPunto + 1).toLowerCase();
    }
}