package com.guillermoinc.training.controller;

import com.guillermoinc.training.dto.CapituloRequestDto;
import com.guillermoinc.training.dto.CapituloResponseDto;
import com.guillermoinc.training.service.CapituloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capitulos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CapituloController {

    private final CapituloService capituloService;

    @PostMapping
    public ResponseEntity<CapituloResponseDto> crearCapitulo(@Valid @RequestBody CapituloRequestDto capituloRequestDto) {
        CapituloResponseDto capituloCreado = capituloService.crearCapitulo(capituloRequestDto);
        return new ResponseEntity<>(capituloCreado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapituloResponseDto> obtenerCapituloPorId(@PathVariable Integer id) {
        CapituloResponseDto capitulo = capituloService.obtenerCapituloPorId(id);
        return ResponseEntity.ok(capitulo);
    }

    @GetMapping
    public ResponseEntity<List<CapituloResponseDto>> obtenerTodosLosCapitulos() {
        List<CapituloResponseDto> capitulos = capituloService.obtenerTodosLosCapitulos();
        return ResponseEntity.ok(capitulos);
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<CapituloResponseDto>> obtenerCapitulosPorCurso(@PathVariable Integer idCurso) {
        List<CapituloResponseDto> capitulos = capituloService.obtenerCapitulosPorCurso(idCurso);
        return ResponseEntity.ok(capitulos);
    }

    @GetMapping("/curso/{idCurso}/ordenados")
    public ResponseEntity<List<CapituloResponseDto>> obtenerCapitulosPorCursoOrdenados(@PathVariable Integer idCurso) {
        List<CapituloResponseDto> capitulos = capituloService.obtenerCapitulosPorCursoOrdenados(idCurso);
        return ResponseEntity.ok(capitulos);
    }

    @GetMapping("/independientes")
    public ResponseEntity<List<CapituloResponseDto>> obtenerCapitulosIndependientes() {
        List<CapituloResponseDto> capitulos = capituloService.obtenerCapitulosIndependientes();
        return ResponseEntity.ok(capitulos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CapituloResponseDto>> buscarCapitulosPorTitulo(@RequestParam String titulo) {
        List<CapituloResponseDto> capitulos = capituloService.buscarCapitulosPorTitulo(titulo);
        return ResponseEntity.ok(capitulos);
    }

    @GetMapping("/curso/{idCurso}/contar")
    public ResponseEntity<Long> contarCapitulosPorCurso(@PathVariable Integer idCurso) {
        Long cantidad = capituloService.contarCapitulosPorCurso(idCurso);
        return ResponseEntity.ok(cantidad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapituloResponseDto> actualizarCapitulo(
            @PathVariable Integer id, 
            @Valid @RequestBody CapituloRequestDto capituloRequestDto) {
        CapituloResponseDto capituloActualizado = capituloService.actualizarCapitulo(id, capituloRequestDto);
        return ResponseEntity.ok(capituloActualizado);
    }

    @PutMapping("/{idCapitulo}/cursos")
    public ResponseEntity<CapituloResponseDto> asociarCapituloConCursos(
            @PathVariable Integer idCapitulo, 
            @RequestBody List<Integer> idsCursos) {
        CapituloResponseDto capituloActualizado = capituloService.asociarCapituloConCursos(idCapitulo, idsCursos);
        return ResponseEntity.ok(capituloActualizado);
    }

    @DeleteMapping("/{idCapitulo}/cursos/{idCurso}")
    public ResponseEntity<CapituloResponseDto> desasociarCapituloDeCurso(
            @PathVariable Integer idCapitulo, 
            @PathVariable Integer idCurso) {
        CapituloResponseDto capituloActualizado = capituloService.desasociarCapituloDeCurso(idCapitulo, idCurso);
        return ResponseEntity.ok(capituloActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCapitulo(@PathVariable Integer id) {
        capituloService.eliminarCapitulo(id);
        return ResponseEntity.noContent().build();
    }
}