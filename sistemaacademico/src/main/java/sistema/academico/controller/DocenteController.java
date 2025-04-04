package sistema.academico.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema.academico.entities.Docente;
import sistema.academico.services.DocenteService;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/docentes")
public class DocenteController {

    private final DocenteService docenteService;

    public DocenteController(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    // Endpoint para registrar un docente
    @PostMapping
    public ResponseEntity<Docente> registrarDocente(@RequestBody Docente docente) {
        Docente nuevoDocente = docenteService.registrarDocente(docente);
        return ResponseEntity.ok(nuevoDocente);
    }

    // Endpoint para actualizar un docente
    @PutMapping("/{id}")
    public ResponseEntity<Docente> actualizarDocente(@PathVariable Long id, @RequestBody Docente docente) {
        docente.setId(id); // Asegurar que el ID se mantiene
        Docente actualizado = docenteService.actualizarDocente(docente);
        return ResponseEntity.ok(actualizado);
    }

    // Endpoint para eliminar un docente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDocente(@PathVariable Long id) {
        docenteService.eliminarDocente(id);
        return ResponseEntity.ok("Docente eliminado correctamente");
    }

    // Endpoint para obtener un docente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Docente> obtenerDocentePorId(@PathVariable Long id) {
        Optional<Docente> docente = docenteService.obtenerDocentePorId(id);
        return docente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todos los docentes
    @GetMapping
    public ResponseEntity<List<Docente>> obtenerTodosLosDocentes() {
        return ResponseEntity.ok(docenteService.obtenerTodosLosDocentes());
    }
}