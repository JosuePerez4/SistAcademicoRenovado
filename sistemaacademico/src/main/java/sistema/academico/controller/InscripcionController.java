package sistema.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema.academico.entities.Curso;
import sistema.academico.entities.Inscripcion;
import sistema.academico.services.InscripcionService;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @PostMapping("/matricula/{matriculaId}/curso/{cursoId}")
    public ResponseEntity<?> inscribirEnCurso(@PathVariable Long matriculaId, @PathVariable Long cursoId) {
        try {
            Inscripcion inscripcion = inscripcionService.inscribirEstudianteEnCurso(matriculaId, cursoId);
            return ResponseEntity.ok(inscripcion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/inscribir/{id}")
    public ResponseEntity<Inscripcion> obtenerInscripcion(@PathVariable Long id) {
        return inscripcionService.obtenerInscripcionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<List<Inscripcion>> listarPorMatricula(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(inscripcionService.listarInscripcionesPorMatricula(matriculaId));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarInscripcion(@PathVariable Long id) {
        return inscripcionService.eliminarInscripcion(id)
                ? ResponseEntity.ok("Inscripción eliminada correctamente")
                : ResponseEntity.badRequest().body("No se pudo eliminar la inscripción");
    }

    @PostMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarInscripcion(@PathVariable Long id, @RequestParam String motivo) {
        return inscripcionService.cancelarInscripcion(id, motivo)
                ? ResponseEntity.ok("Inscripción cancelada")
                : ResponseEntity.badRequest().body("No se pudo cancelar la inscripción");
    }

    @PostMapping("/nota/{id}")
    public ResponseEntity<String> registrarNotaFinal(@PathVariable Long id, @RequestParam Double notaFinal) {
        return inscripcionService.registrarNotaFinal(id, notaFinal)
                ? ResponseEntity.ok("Nota final registrada")
                : ResponseEntity.badRequest().body("No se pudo registrar la nota final");
    }

    @GetMapping("/aprobado/{id}")
    public ResponseEntity<Boolean> cursoAprobado(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.cursoAprobado(id));
    }

    @GetMapping("/aprobados/matricula/{matriculaId}")
    public ResponseEntity<Long> contarCursosAprobados(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(inscripcionService.contarCursosAprobadosPorMatricula(matriculaId));
    }

    @GetMapping("/cursos/matricula/{matriculaId}")
    public ResponseEntity<List<Curso>> obtenerCursosPorMatricula(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(inscripcionService.obtenerCursosPorMatricula(matriculaId));
    }

    @GetMapping("/existe")
    public ResponseEntity<Boolean> existeInscripcion(@RequestParam Long matriculaId, @RequestParam Long cursoId) {
        return ResponseEntity.ok(inscripcionService.existeInscripcion(matriculaId, cursoId));
    }

    @GetMapping("/activas/curso/{cursoId}")
    public ResponseEntity<List<Inscripcion>> listarActivasPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(inscripcionService.listarInscripcionesActivasPorCurso(cursoId));
    }

    @GetMapping("/contar/curso/{cursoId}")
    public ResponseEntity<Long> contarInscritosCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(inscripcionService.contarInscritosEnCurso(cursoId));
    }

    @GetMapping("/nota")
    public ResponseEntity<Double> obtenerNotaFinal(@RequestParam Long matriculaId, @RequestParam Long cursoId) {
        Double nota = inscripcionService.obtenerNotaFinal(matriculaId, cursoId);
        return nota != null ? ResponseEntity.ok(nota) : ResponseEntity.notFound().build();
    }

    @PostMapping("/estudiante/{estudianteId}/materia/{materiaId}")
    public ResponseEntity<?> inscribirEnMateria(@PathVariable Long estudianteId, @PathVariable Long materiaId) {
        try {
            Inscripcion inscripcion = inscripcionService.inscribirEstudianteEnMateria(estudianteId, materiaId);
            return ResponseEntity.ok(inscripcion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}