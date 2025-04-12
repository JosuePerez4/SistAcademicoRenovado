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

import sistema.academico.DTO.EstudianteRequestDTO;
import sistema.academico.DTO.EstudianteUpdateDTO;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.ProgramaAcademico;
import sistema.academico.repository.ProgramaAcademicoRepository;
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

    @Autowired
    private ProgramaAcademicoRepository programaAcademicoRepository;

    // Crear un estudiante
    @PostMapping("/registrar")
    public ResponseEntity<Estudiante> registrarEstudiante(@RequestBody EstudianteRequestDTO dto) {
        ProgramaAcademico programa = programaAcademicoRepository
                .findById(dto.getProgramaAcademicoId())
                .orElseThrow(() -> new RuntimeException("Programa académico no encontrado"));

        Estudiante estudiante = new Estudiante();
        estudiante.setCedula(dto.getCedula());
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellido(dto.getApellido());
        estudiante.setDireccion(dto.getDireccion());
        estudiante.setCorreo(dto.getCorreo());
        estudiante.setTelefono(dto.getTelefono());
        estudiante.setGenero(dto.getGenero());
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());
        estudiante.setCodigo(dto.getCodigo());
        estudiante.setContrasena(dto.getContrasena());
        estudiante.setEstado(dto.isEstado());
        estudiante.setRol(dto.getRol());
        estudiante.setPromedio(dto.getPromedio());
        estudiante.setBeca(dto.isBeca());
        estudiante.setFechaIngreso(dto.getFechaIngreso());
        estudiante.setFechaEgreso(dto.getFechaEgreso());
        estudiante.setProgramaAcademico(programa);

        Estudiante guardado = estudianteService.registrarEstudiante(estudiante);
        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

    // Actualizar un estudiante
    @PutMapping("actualizar/{codigo}") // Para actualizar
    public ResponseEntity<?> actualizarEstudiante(@PathVariable String codigo,
            @RequestBody EstudianteUpdateDTO estudianteActualizado) {

        // Buscar y verificar si el estudiante que queremos actualizar existe
        Estudiante estudianteEncontrao = estudianteService.buscarPorCodigo(codigo);

        if (estudianteEncontrao != null) {
            estudianteEncontrao.setNombre(estudianteActualizado.getNombre());
            estudianteEncontrao.setApellido(estudianteActualizado.getApellido());
            estudianteEncontrao.setDireccion(estudianteActualizado.getDireccion());
            estudianteEncontrao.setCorreo(estudianteActualizado.getCorreo());
            estudianteEncontrao.setTelefono(estudianteActualizado.getTelefono());
            estudianteEncontrao.setEstado(estudianteActualizado.isEstado());
            estudianteEncontrao.setBeca(estudianteActualizado.isBeca());
            estudianteEncontrao.setFechaEgreso(estudianteActualizado.getFechaEgreso());
            return ResponseEntity.ok(estudianteEncontrao);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("El estudiante con el código " + codigo + " no fue encontrado y no se pudo actualizar.");
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
