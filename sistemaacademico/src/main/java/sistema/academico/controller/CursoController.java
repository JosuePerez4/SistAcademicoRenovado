package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.academico.entities.Curso;
import sistema.academico.services.CursoService;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping("/crear")
    public ResponseEntity<Curso> crearCurso(@RequestBody Curso curso) {
        return ResponseEntity.ok(cursoService.crearCurso(curso));
    }

    @PutMapping("/modificar")
    public ResponseEntity<Curso> modificarCurso(@RequestBody Curso curso) {
        return ResponseEntity.ok(cursoService.modificarCurso(curso));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.ok("Curso eliminado correctamente.");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Curso>> listarTodosLosCursos() {
        return ResponseEntity.ok(cursoService.listarTodosLosCursos());
    }
}