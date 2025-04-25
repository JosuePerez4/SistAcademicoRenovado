package sistema.academico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.academico.DTO.EvaluacionRequestDTO;
import sistema.academico.DTO.EvaluacionResponseDTO;
import sistema.academico.services.EvaluacionService;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    @PostMapping
    public ResponseEntity<EvaluacionResponseDTO> crearEvaluacion(@RequestBody EvaluacionRequestDTO dto) {
        EvaluacionResponseDTO evaluacionCreada = evaluacionService.crearEvaluacion(dto);
        return ResponseEntity.ok(evaluacionCreada);
    }

    // 1. Listar evaluaciones por curso
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<EvaluacionResponseDTO>> listarPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(evaluacionService.obtenerEvaluacionesPorCurso(cursoId));
    }

    // 2. Actualizar evaluación
    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionResponseDTO> actualizarEvaluacion(@PathVariable Long id,
                                                                       @RequestBody EvaluacionRequestDTO dto) {
        return ResponseEntity.ok(evaluacionService.actualizarEvaluacion(id, dto));
    }

    // 3. Eliminar evaluación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvaluacion(@PathVariable Long id) {
        evaluacionService.eliminarEvaluacion(id);
        return ResponseEntity.noContent().build();
    }
}
