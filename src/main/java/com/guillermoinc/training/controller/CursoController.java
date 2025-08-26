package com.guillermoinc.training.controller;

import com.guillermoinc.training.dto.CursoRequestDto;
import com.guillermoinc.training.dto.CursoResponseDto;
import com.guillermoinc.training.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoResponseDto> crearCurso(@Valid @RequestBody CursoRequestDto cursoRequestDto) {
        CursoResponseDto cursoCreado = cursoService.crearCurso(cursoRequestDto);
        return new ResponseEntity<>(cursoCreado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDto> obtenerCursoPorId(@PathVariable Integer id) {
        CursoResponseDto curso = cursoService.obtenerCursoPorId(id);
        return ResponseEntity.ok(curso);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponseDto>> obtenerTodosLosCursos() {
        List<CursoResponseDto> cursos = cursoService.obtenerTodosLosCursos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CursoResponseDto>> obtenerCursosPorEstado(@PathVariable String estado) {
        List<CursoResponseDto> cursos = cursoService.obtenerCursosPorEstado(estado);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<CursoResponseDto>> obtenerCursosPorCategoria(@PathVariable String categoria) {
        List<CursoResponseDto> cursos = cursoService.obtenerCursosPorCategoria(categoria);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<CursoResponseDto>> obtenerCursosPorNivel(@PathVariable String nivel) {
        List<CursoResponseDto> cursos = cursoService.obtenerCursosPorNivel(nivel);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CursoResponseDto>> buscarCursosPorTitulo(@RequestParam String titulo) {
        List<CursoResponseDto> cursos = cursoService.buscarCursosPorTitulo(titulo);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<CursoResponseDto>> obtenerCursosPorCategoriaYNivel(
            @RequestParam String categoria, 
            @RequestParam String nivel) {
        List<CursoResponseDto> cursos = cursoService.obtenerCursosPorCategoriaYNivel(categoria, nivel);
        return ResponseEntity.ok(cursos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDto> actualizarCurso(
            @PathVariable Integer id, 
            @Valid @RequestBody CursoRequestDto cursoRequestDto) {
        CursoResponseDto cursoActualizado = cursoService.actualizarCurso(id, cursoRequestDto);
        return ResponseEntity.ok(cursoActualizado);
    }

    @PutMapping("/{idCurso}/capitulos")
    public ResponseEntity<CursoResponseDto> asociarCursoConCapitulos(
            @PathVariable Integer idCurso, 
            @RequestBody List<Integer> idsCapitulos) {
        CursoResponseDto cursoActualizado = cursoService.asociarCursoConCapitulos(idCurso, idsCapitulos);
        return ResponseEntity.ok(cursoActualizado);
    }

    @DeleteMapping("/{idCurso}/capitulos/{idCapitulo}")
    public ResponseEntity<CursoResponseDto> desasociarCursoDeCapitulo(
            @PathVariable Integer idCurso, 
            @PathVariable Integer idCapitulo) {
        CursoResponseDto cursoActualizado = cursoService.desasociarCursoDeCapitulo(idCurso, idCapitulo);
        return ResponseEntity.ok(cursoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Integer id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}