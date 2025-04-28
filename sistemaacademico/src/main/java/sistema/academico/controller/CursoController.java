package sistema.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema.academico.DTO.CrearCursoRequestDTO;
import sistema.academico.DTO.ListarCursoPorMateriaDTO;
import sistema.academico.DTO.ListarHorarioPorCursoDTO;
import sistema.academico.services.CursoService;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // Crear un nuevo curso
    @PostMapping("/crear")
    public CrearCursoRequestDTO crearCurso(@RequestBody CrearCursoRequestDTO curso) {
        return cursoService.crearCurso(curso);
    }

    // Modificar un curso existente
    @PutMapping("/modificar/{cursoId}")
    public CrearCursoRequestDTO modificarCurso(@RequestBody CrearCursoRequestDTO curso, @PathVariable long cursoId) {
        return cursoService.modificarCurso(curso, cursoId);
    }

    // Eliminar un curso
    @DeleteMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable Long id) {
        return cursoService.eliminarCurso(id);
    }

    // Listar cursos por materia
    @GetMapping("/listar/materia/{materiaId}")
    public List<ListarCursoPorMateriaDTO> listarCursosPorMateria(@PathVariable Long materiaId) {
        return cursoService.listarCursosPorMateria(materiaId);
    }

    // Listar todos los cursos
    @GetMapping("/listar/todos")
    public List<ListarCursoPorMateriaDTO> listarTodosLosCursos() {
        return cursoService.listarTodosLosCursos();
    }

    @GetMapping("/cursos/{idCurso}/horarios")
    public ResponseEntity<List<ListarHorarioPorCursoDTO>> listarHorarios(@PathVariable Long idCurso) {
        try {
            List<ListarHorarioPorCursoDTO> horarios = cursoService.listarHorarios(idCurso);
            return ResponseEntity.ok(horarios);
        } catch (RuntimeException e) {
            // Manejar error en caso de que el curso no se encuentre
            return ResponseEntity.status(404).body(null);
        }
    }
}
