package com.guillermoinc.training.controller;

import com.guillermoinc.training.dto.ContenidoResponseDto;
import com.guillermoinc.training.dto.ContenidoConDatosResponseDto;
import com.guillermoinc.training.service.ContenidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/contenidos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContenidoController {

    private final ContenidoService contenidoService;

    @PostMapping("/cargar")
    public ResponseEntity<ContenidoResponseDto> cargar(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("idCurso") Integer idCurso) {
        
        // Validaciones básicas
        if (archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede estar vacío");
        }
        
        if (archivo.getOriginalFilename() == null || archivo.getOriginalFilename().trim().isEmpty()) {
            throw new IllegalArgumentException("El archivo debe tener un nombre válido");
        }
        
        // Validar tamaño máximo (ejemplo: 50MB)
        long maxSize = 50 * 1024 * 1024; // 50MB en bytes
        if (archivo.getSize() > maxSize) {
            throw new IllegalArgumentException("El archivo excede el tamaño máximo permitido (50MB)");
        }
        
        ContenidoResponseDto contenidoCargado = contenidoService.cargar(archivo, idCurso);
        return new ResponseEntity<>(contenidoCargado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContenidoResponseDto> obtenerContenido(@PathVariable Integer id) {
        ContenidoResponseDto contenido = contenidoService.obtenerContenidoPorId(id);
        return ResponseEntity.ok(contenido);
    }

    @GetMapping("/{id}/descargar")
    public ResponseEntity<byte[]> descargarContenido(@PathVariable Integer id) {
        ContenidoConDatosResponseDto contenido = contenidoService.descargarContenido(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", contenido.getNombreArchivo());
        headers.setContentLength(contenido.getTamaño());
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(contenido.getDatos());
    }

    @GetMapping
    public ResponseEntity<List<ContenidoResponseDto>> obtenerTodosLosContenidos() {
        List<ContenidoResponseDto> contenidos = contenidoService.obtenerTodosLosContenidos();
        return ResponseEntity.ok(contenidos);
    }

    @GetMapping("/extension/{extension}")
    public ResponseEntity<List<ContenidoResponseDto>> buscarPorExtension(@PathVariable String extension) {
        List<ContenidoResponseDto> contenidos = contenidoService.buscarContenidosPorExtension(extension);
        return ResponseEntity.ok(contenidos);
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<ContenidoResponseDto>> obtenerContenidosPorCurso(@PathVariable Integer idCurso) {
        List<ContenidoResponseDto> contenidos = contenidoService.obtenerContenidosPorCurso(idCurso);
        return ResponseEntity.ok(contenidos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarContenido(@PathVariable Integer id) {
        contenidoService.eliminarContenido(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno: " + e.getMessage());
    }
}