package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sistema.academico.DTO.*;
import sistema.academico.entities.*;
import sistema.academico.enums.EstadoMatricula;
import sistema.academico.services.MatriculaService;
import sistema.academico.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private ProgramaAcademicoRepository programaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping("/{id}")
    public MatriculaResponseDTO obtenerPorId(@PathVariable Long id) {
        return convertirAMatriculaResponseDTO(matriculaService.obtenerMatriculaPorId(id));
    }

    @GetMapping
    public List<MatriculaResponseDTO> obtenerTodas() {
        return matriculaService.obtenerTodas()
                .stream().map(this::convertirAMatriculaResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/estudiante/{estudianteId}")
    public List<MatriculaResponseDTO> obtenerPorEstudiante(@PathVariable Long estudianteId) {
        return matriculaService.obtenerPorEstudiante(estudianteId)
                .stream().map(this::convertirAMatriculaResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/semestre/{semestreId}")
    public List<MatriculaResponseDTO> obtenerPorSemestre(@PathVariable Long semestreId) {
        return matriculaService.obtenerPorSemestre(semestreId)
                .stream().map(this::convertirAMatriculaResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/estado/{estado}")
    public List<MatriculaResponseDTO> obtenerPorEstado(@PathVariable EstadoMatricula estado) {
        return matriculaService.obtenerPorEstado(estado)
                .stream().map(this::convertirAMatriculaResponseDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/registrar-primera-vez")
    public MatriculaResponseDTO registrarPrimeraVez(@RequestBody MatriculaRequestDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.getEstudianteId()).orElseThrow();
        Semestre semestre = semestreRepository.findById(dto.getSemestreId()).orElseThrow();
        ProgramaAcademico programa = programaRepository.findById(dto.getProgramaId()).orElse(null);

        Matricula matricula = matriculaService.matricularEstudiantePrimeraVez(estudiante, semestre, programa);
        return convertirAMatriculaResponseDTO(matricula);
    }

    @PostMapping("/registrar")
    public boolean registrarMatricula(@RequestParam Long estudianteId,
                                      @RequestParam Long semestreId,
                                      @RequestParam List<Long> cursosIds) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId).orElseThrow();
        Semestre semestre = semestreRepository.findById(semestreId).orElseThrow();
        List<Curso> cursos = cursoRepository.findAllById(cursosIds);

        return matriculaService.matricularEstudiante(estudiante, semestre, cursos);
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
    public boolean actualizarEstado(@PathVariable Long id, @RequestBody MatriculaUpdateDTO dto) {
        return matriculaService.actualizarEstadoMatricula(id, dto.getEstado());
    }

    // 🛠️ Método de utilidad para convertir a DTO
    private MatriculaResponseDTO convertirAMatriculaResponseDTO(Matricula matricula) {
        MatriculaResponseDTO dto = new MatriculaResponseDTO();
        dto.setId(matricula.getId());
        dto.setFechaMatricula(matricula.getFechaMatricula());
        dto.setFechaCancelacion(matricula.getFechaCancelacion());
        dto.setMotivoCancelacion(matricula.getMotivoCancelacion());
        dto.setEstado(matricula.getEstado());

        if (matricula.getEstudiante() != null)
            dto.setEstudianteId(matricula.getEstudiante().getId());
        if (matricula.getSemestre() != null)
            dto.setSemestreId(matricula.getSemestre().getId());
        if (matricula.getPrograma() != null)
            dto.setProgramaId(matricula.getPrograma().getId());
        if (matricula.getInscripciones() != null)
            dto.setInscripcionesIds(matricula.getInscripciones()
                    .stream().map(Inscripcion::getId).collect(Collectors.toList()));

        return dto;
    }
}