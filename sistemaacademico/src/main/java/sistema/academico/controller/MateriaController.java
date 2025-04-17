package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.academico.entities.Materia;
import sistema.academico.services.MateriaService;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @PostMapping("/crear")
    public ResponseEntity<Materia> crearMateria(@RequestBody Materia materia) {
        return ResponseEntity.ok(materiaService.registrarMateria(materia));
    }

    @PutMapping("/modificar")
    public ResponseEntity<Materia> modificarMateria(@RequestBody Materia materia) {
        return ResponseEntity.ok(materiaService.actualizarMateria(materia));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarMateria(@PathVariable Long id) {
        materiaService.eliminarMateria(id);
        return ResponseEntity.ok("Materia eliminada correctamente.");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Materia>> listarTodasLasMaterias() {
        return ResponseEntity.ok(materiaService.obtenerTodasLasMaterias());
    }
}