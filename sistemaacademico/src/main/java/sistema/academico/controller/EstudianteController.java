package sistema.academico.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sistema.academico.entities.Estudiante;
import sistema.academico.services.EstudianteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/estudiante")
@CrossOrigin(origins = "*")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    // Registrar un estudiante
    @PostMapping("/registrar")
    public ResponseEntity<Estudiante> registrarEstudiante(@RequestBody Estudiante estudiante) {
        Estudiante nuevo = estudianteService.registrarEstudiante(estudiante);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // Actualizar un estudiante
    @PutMapping("actualizar/{codigo}") // Para actualizar
    public ResponseEntity<Estudiante> actualizarEstudiante(
            @PathVariable String codigo,
            @RequestBody Estudiante estudianteActualizado) {

        Estudiante estudiante = estudianteService.actualizarEstudiante(codigo, estudianteActualizado);
        return ResponseEntity.ok(estudiante);
    }

    // Eliminar un estudiante
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarEstudiante(@PathVariable long idEstudiante) {

        boolean eliminado = estudianteService.eliminarEstudiante(idEstudiante);
        if (eliminado) {
            return ResponseEntity.ok("Estudiante con código " + idEstudiante + " eliminado correctamente");
        }
        return ResponseEntity.status(404).body("Estudiante con código " + idEstudiante + " no encontrado");
    }

    // Obtener un estudiante por id
    @GetMapping("/obtener/{id}")
    public ResponseEntity<Estudiante> obtenerEstudiante(@PathVariable long idEstudiante) {
        Optional<Estudiante> estudiante = estudianteService.obtenerEstudiantePorId(idEstudiante);

        return estudiante.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    // Obtener todos los estudiantes
    @GetMapping("/obtenerTodos")
    public List<Estudiante> obtenerEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.obtenerTodos();
        return estudiantes;
    }
}
