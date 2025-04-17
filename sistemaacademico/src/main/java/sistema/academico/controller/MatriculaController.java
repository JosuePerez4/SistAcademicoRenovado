package sistema.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sistema.academico.DTO.ActualizarEstadoMatriculaRequestDTO;
import sistema.academico.DTO.ActualizarEstadoMatriculaResponseDTO;
import sistema.academico.DTO.MatriculaEstadoDTO;
import sistema.academico.DTO.MatriculaEstudianteExistenteRequestDTO;
import sistema.academico.DTO.MatriculaEstudianteExistenteResponsetDTO;
import sistema.academico.DTO.MatriculaPrimeraVezRequestDTO;
import sistema.academico.DTO.MatriculaPrimeraVezResponseDTO;
import sistema.academico.DTO.MatriculaResponseDTO;
import sistema.academico.DTO.MatriculaResumenEstudianteDTO;
import sistema.academico.enums.EstadoMatricula;
import sistema.academico.services.MatriculaService;

@RestController
@RequestMapping("/api/matricula")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @GetMapping("/obtenerPorId/{id}")
    public MatriculaResponseDTO obtenerPorId(@PathVariable Long id) {
        return matriculaService.obtenerMatriculaPorId(id);
    }

    @GetMapping("/obtenerTodas")
    public List<MatriculaResponseDTO> obtenerTodas() {
        return matriculaService.obtenerTodas();
    }

    @GetMapping("/obtenerPorEstudiante/{estudianteId}")
    public List<MatriculaResumenEstudianteDTO> obtenerPorEstudiante(@PathVariable Long estudianteId) {
        return matriculaService.obtenerPorEstudiante(estudianteId);
    }

    @GetMapping("/obtenerPorSemestre/{semestreId}")
    public List<MatriculaResumenEstudianteDTO> obtenerPorSemestre(@PathVariable Long semestreId) {
        return matriculaService.obtenerPorSemestre(semestreId);
    }

    @GetMapping("/estado/{estado}")
    public List<MatriculaEstadoDTO> obtenerPorEstado(@PathVariable EstadoMatricula estado) {
        return matriculaService.obtenerPorEstado(estado);
    }

    // Matricular por primera vez con IDs
    @PostMapping("/registrar-primera-vez")
    public MatriculaPrimeraVezResponseDTO registrarPrimeraVez(
            @RequestBody MatriculaPrimeraVezRequestDTO dto) {
        return matriculaService.matricularEstudiantePrimeraVez(dto);
    }

    // Matricular con lista de cursos (separados por coma)
    @PostMapping("/registrar")
    public MatriculaEstudianteExistenteResponsetDTO registrarMatricula(
            @RequestBody MatriculaEstudianteExistenteRequestDTO dto) {
        return matriculaService.matricularEstudiante(dto);
    }

    @GetMapping("/{id}/cursos-aprobados")
    public long contarCursosAprobados(@PathVariable Long id) {
        return matriculaService.contarCursosAprobados(id);
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarMatricula(@PathVariable Long id, @RequestParam String motivo) {
        return matriculaService.cancelarMatricula(motivo, id);
    }

    @PutMapping("actualizarEstado/{id}")
    public ActualizarEstadoMatriculaResponseDTO actualizarEstado(@RequestBody ActualizarEstadoMatriculaRequestDTO dto) {
        return matriculaService.actualizarEstadoMatricula(dto);
    }
}
