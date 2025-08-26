package com.guillermoinc.training.controller;

import com.guillermoinc.training.dto.ProgresoUsuarioCursoRequestDto;
import com.guillermoinc.training.dto.ProgresoUsuarioCursoResponseDto;
import com.guillermoinc.training.service.ProgresoUsuarioCursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/progreso-cursos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProgresoUsuarioCursoController {

    private final ProgresoUsuarioCursoService progresoService;

    /**
     * Registra el progreso de un usuario en un curso
     */
    @PostMapping("/registrar")
    public ResponseEntity<ProgresoUsuarioCursoResponseDto> registrarProgreso(
            @RequestParam("idUsuario") Integer idUsuario,
            @RequestParam("idCurso") Integer idCurso,
            @RequestParam("porcentaje") BigDecimal porcentaje) {
        
        ProgresoUsuarioCursoResponseDto progreso = progresoService.registrarProgreso(idUsuario, idCurso, porcentaje);
        return new ResponseEntity<>(progreso, HttpStatus.CREATED);
    }

    /**
     * Actualiza el progreso de un usuario en un curso
     */
    @PutMapping("/actualizar")
    public ResponseEntity<ProgresoUsuarioCursoResponseDto> actualizarProgreso(
            @RequestParam("idUsuario") Integer idUsuario,
            @RequestParam("idCurso") Integer idCurso,
            @RequestParam("porcentaje") BigDecimal porcentaje) {
        
        ProgresoUsuarioCursoResponseDto progreso = progresoService.actualizarProgreso(idUsuario, idCurso, porcentaje);
        return ResponseEntity.ok(progreso);
    }

    /**
     * Obtiene el progreso específico de un usuario en un curso
     */
    @GetMapping("/usuario/{idUsuario}/curso/{idCurso}")
    public ResponseEntity<ProgresoUsuarioCursoResponseDto> obtenerProgreso(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idCurso) {
        
        ProgresoUsuarioCursoResponseDto progreso = progresoService.obtenerProgreso(idUsuario, idCurso);
        if (progreso != null) {
            return ResponseEntity.ok(progreso);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene todos los progresos de un usuario
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ProgresoUsuarioCursoResponseDto>> obtenerProgresosPorUsuario(
            @PathVariable Integer idUsuario) {
        
        List<ProgresoUsuarioCursoResponseDto> progresos = progresoService.obtenerProgresosPorUsuario(idUsuario);
        return ResponseEntity.ok(progresos);
    }

    /**
     * Obtiene todos los progresos de un curso
     */
    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<ProgresoUsuarioCursoResponseDto>> obtenerProgresosPorCurso(
            @PathVariable Integer idCurso) {
        
        List<ProgresoUsuarioCursoResponseDto> progresos = progresoService.obtenerProgresosPorCurso(idCurso);
        return ResponseEntity.ok(progresos);
    }

    /**
     * Marca un curso como completado para un usuario
     */
    @PostMapping("/completar")
    public ResponseEntity<ProgresoUsuarioCursoResponseDto> completarCurso(
            @RequestParam("idUsuario") Integer idUsuario,
            @RequestParam("idCurso") Integer idCurso) {
        
        ProgresoUsuarioCursoResponseDto progreso = progresoService.completarCurso(idUsuario, idCurso);
        return ResponseEntity.ok(progreso);
    }

    /**
     * Crea o actualiza progreso usando un DTO completo
     */
    @PostMapping
    public ResponseEntity<ProgresoUsuarioCursoResponseDto> crearOActualizarProgreso(
            @Valid @RequestBody ProgresoUsuarioCursoRequestDto progresoDto) {
        
        ProgresoUsuarioCursoResponseDto progreso = progresoService.crearOActualizarProgreso(progresoDto);
        return new ResponseEntity<>(progreso, HttpStatus.CREATED);
    }

    /**
     * Manejo de errores para argumentos inválidos
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Manejo de errores generales
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno: " + e.getMessage());
    }
}