package sistema.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sistema.academico.entities.*;
import sistema.academico.enums.EstadoMatricula;
import sistema.academico.services.MatriculaService;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @GetMapping("/obtenerPorId/{id}")
    public Matricula obtenerPorId(@PathVariable Long id) {
        return matriculaService.obtenerMatriculaPorId(id);
    }

    @GetMapping("/obtenerTodas")
    public List<Matricula> obtenerTodas() {
        return matriculaService.obtenerTodas();
    }

    @GetMapping("/estudiante/{estudianteId}")
    public List<Matricula> obtenerPorEstudiante(@PathVariable Long estudianteId) {
        return matriculaService.obtenerPorEstudiante(estudianteId);
    }

    @GetMapping("/semestre/{semestreId}")
    public List<Matricula> obtenerPorSemestre(@PathVariable Long semestreId) {
        return matriculaService.obtenerPorSemestre(semestreId);
    }

    @GetMapping("/estado/{estado}")
    public List<Matricula> obtenerPorEstado(@PathVariable EstadoMatricula estado) {
        return matriculaService.obtenerPorEstado(estado);
    }

    // Matricular por primera vez con IDs
    @PostMapping("/registrar-primera-vez")
    public Matricula registrarPrimeraVez(
            @RequestParam Estudiante estudianteId,
            @RequestParam Semestre semestreId,
            @RequestParam ProgramaAcademico programaId) {
        return matriculaService.matricularEstudiantePrimeraVez(estudianteId, semestreId, programaId);
    }
    
    // Matricular con lista de cursos (separados por coma)
    @PostMapping("/registrar")
    public boolean registrarMatricula(
            @RequestParam Estudiante estudianteId,
            @RequestParam Semestre semestreId,
            @RequestParam List<Curso> cursoIds) {
        return matriculaService.matricularEstudiante(estudianteId, semestreId, cursoIds);
    }

    @GetMapping("/{id}/promedio")
    public double calcularPromedio(@PathVariable Long id) {
        return matriculaService.calcularPromedio(id);
    }

    @GetMapping("/{id}/cursos-aprobados")
    public long contarCursosAprobados(@PathVariable Long id) {
        return matriculaService.contarCursosAprobados(id);
    }

    @PostMapping("/{id}/cancelar")
    public boolean cancelarMatricula(@PathVariable Long id, @RequestParam String motivo) {
        return matriculaService.cancelarMatricula(motivo, id);
    }

    @PutMapping("/{id}/estado")
    public boolean actualizarEstado(@PathVariable Long id, @RequestParam EstadoMatricula nuevoEstado) {
        return matriculaService.actualizarEstadoMatricula(id, nuevoEstado);
    }
}
