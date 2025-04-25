package sistema.academico.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistema.academico.DTO.EvaluacionRequestDTO;
import sistema.academico.DTO.EvaluacionResponseDTO;
import sistema.academico.entities.Calificacion;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Evaluacion;
import sistema.academico.entities.Inscripcion;
import sistema.academico.enums.TipoEvaluacion;
import sistema.academico.repository.CursoRepository;
import sistema.academico.repository.EvaluacionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepo;
    private final CursoRepository cursoRepo;

    public EvaluacionResponseDTO crearEvaluacion(EvaluacionRequestDTO dto) {
        Curso curso = cursoRepo.findById(dto.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Verificar unicidad para tipos únicos
        if (esTipoUnico(dto.getTipo())) {
            boolean existe = curso.getEvaluaciones().stream()
                    .anyMatch(e -> e.getTipo() == dto.getTipo());
            if (existe) throw new RuntimeException("Ya existe una evaluación de tipo " + dto.getTipo() + " para este curso");
        }

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setTipo(dto.getTipo());
        evaluacion.setDescripcion(dto.getDescripcion());
        evaluacion.setFechaCreacion(dto.getFechaCreacion());
        evaluacion.setFechaLimite(dto.getFechaLimite());
        evaluacion.setCurso(curso);

        if (esTipoUnico(dto.getTipo())) {
            List<Calificacion> calificaciones = curso.getInscripciones().stream()
                    .map(inscripcion -> {
                        Calificacion calificacion = new Calificacion();
                        calificacion.setEstudiante(inscripcion.getEstudiante());
                        calificacion.setNota(0.0); // nota vacía
                        calificacion.setEvaluacion(evaluacion);
                        return calificacion;
                    }).collect(Collectors.toList());

            evaluacion.setCalificaciones(calificaciones);
        }

        evaluacionRepo.save(evaluacion);
        return mapToDTO(evaluacion);
    }

    public List<EvaluacionResponseDTO> obtenerEvaluacionesPorCurso(Long cursoId) {
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        return curso.getEvaluaciones().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public EvaluacionResponseDTO actualizarEvaluacion(Long id, EvaluacionRequestDTO dto) {
        Evaluacion evaluacion = evaluacionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));

        // No se permite cambiar el tipo ni el curso
        evaluacion.setDescripcion(dto.getDescripcion());
        evaluacion.setFechaCreacion(dto.getFechaCreacion());
        evaluacion.setFechaLimite(dto.getFechaLimite());

        evaluacionRepo.save(evaluacion);
        return mapToDTO(evaluacion);
    }

    public void eliminarEvaluacion(Long id) {
        Evaluacion evaluacion = evaluacionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));
        evaluacionRepo.delete(evaluacion);
    }

    private boolean esTipoUnico(TipoEvaluacion tipo) {
        return tipo == TipoEvaluacion.PARCIAL_1 || tipo == TipoEvaluacion.PARCIAL_2 ||
               tipo == TipoEvaluacion.TERCERA_NOTA || tipo == TipoEvaluacion.PARCIAL_FINAL;
    }

    private EvaluacionResponseDTO mapToDTO(Evaluacion evaluacion) {
        EvaluacionResponseDTO dto = new EvaluacionResponseDTO();
        dto.setId(evaluacion.getId());
        dto.setTipo(evaluacion.getTipo());
        dto.setDescripcion(evaluacion.getDescripcion());
        dto.setFechaCreacion(evaluacion.getFechaCreacion());
        dto.setFechaLimite(evaluacion.getFechaLimite());
        dto.setCursoId(evaluacion.getCurso().getId());
        return dto;
    }
}
