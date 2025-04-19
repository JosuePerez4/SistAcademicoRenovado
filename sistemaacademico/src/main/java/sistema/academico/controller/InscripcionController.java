package sistema.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema.academico.DTO.EstadoInscripcionResponseDTO;
import sistema.academico.DTO.InscripcionesPorMatriculaResponseDTO;
import sistema.academico.DTO.MatricularCursoResponseDTO;
import sistema.academico.services.InscripcionService;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @PostMapping("/matricular/{matriculaId}/curso/{cursoId}")
    public MatricularCursoResponseDTO inscribirEnCurso(@PathVariable Long matriculaId, @PathVariable Long cursoId) {
        return inscripcionService.inscribirEstudianteEnCurso(matriculaId, cursoId);
    }

    @GetMapping("/obtenerInscripcion/{id}")
    public MatricularCursoResponseDTO obtenerInscripcion(@PathVariable Long id) {
        return inscripcionService.obtenerInscripcionPorId(id);
    }

    @GetMapping("/inscripcionesPorMatricula/{matriculaId}")
    public InscripcionesPorMatriculaResponseDTO listarPorMatricula(@PathVariable Long matriculaId) {
        return inscripcionService.listarInscripcionesPorMatricula(matriculaId);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarInscripcion(@PathVariable Long id) {
        return inscripcionService.eliminarInscripcion(id)
                ? ResponseEntity.ok("Inscripción eliminada correctamente")
                : ResponseEntity.badRequest().body("No se pudo eliminar la inscripción");
    }

    @PostMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarInscripcion(@PathVariable Long id) {
        return inscripcionService.cancelarInscripcion(id)
                ? ResponseEntity.ok("Inscripción cancelada")
                : ResponseEntity.badRequest().body("No se pudo cancelar la inscripción");
    }

    @GetMapping("/estadoCurso/{id}")
    public ResponseEntity<?> estadoCurso(@PathVariable Long id) {
        EstadoInscripcionResponseDTO estado = inscripcionService.estadoCurso(id);
        return estado != null
                ? ResponseEntity.ok(estado)
                : ResponseEntity.badRequest().body("El curso no está inscrito");
    }

    @GetMapping("/cantidadInscripciones/aprobadas/porMatricula/{matriculaId}")
    public ResponseEntity<Long> contarCursosAprobados(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(inscripcionService.contarCursosAprobadosPorMatricula(matriculaId));
    }

    @GetMapping("/existe")
    public ResponseEntity<Boolean> existeInscripcion(@RequestParam Long matriculaId, @RequestParam Long cursoId) {
        return ResponseEntity.ok(inscripcionService.existeInscripcion(matriculaId, cursoId));
    }

    @GetMapping("/contarInscripciones/activas/porCurso/{cursoId}")
    public int contarActivasPorCurso(@PathVariable Long cursoId) {
        return inscripcionService.contarInscripcionesActivasPorCurso(cursoId);
    }

    @GetMapping("/inscripciones/activas/porCurso/{cursoId}")
    public List<MatricularCursoResponseDTO> listarActivasPorCurso(@PathVariable Long cursoId) {
        return inscripcionService.listarInscripcionesActivasPorCurso(cursoId);
    }

    @GetMapping("/cantidadVecesInscrito/curso/{cursoId}")
    public ResponseEntity<Long> contarInscritosCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(inscripcionService.contarInscritosEnCurso(cursoId));
    }

    @GetMapping("/nota")
    public String obtenerNotaFinal(@RequestParam Long matriculaId, @RequestParam Long cursoId) {
        Double nota = inscripcionService.obtenerNotaFinal(matriculaId, cursoId);
        return nota != null ? String.valueOf(nota) : "No se tiene nota final aún";
    }
}