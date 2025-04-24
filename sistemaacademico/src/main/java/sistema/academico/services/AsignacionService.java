package sistema.academico.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistema.academico.DTO.AsignacionCursoDTO;
import sistema.academico.DTO.AsignacionCursoResponseDTO;
import sistema.academico.entities.AsignacionCurso;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Docente;
import sistema.academico.repository.AsignacionCursoRepository;
import sistema.academico.repository.CursoRepository;
import sistema.academico.repository.DocenteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AsignacionService {

    private final AsignacionCursoRepository asignacionRepo;
    private final DocenteRepository docenteRepo;
    private final CursoRepository cursoRepo;

    public AsignacionCursoResponseDTO asignarCurso(AsignacionCursoDTO dto) {
        Docente docente = docenteRepo.findById(dto.getDocenteId())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        Curso curso = cursoRepo.findById(dto.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        AsignacionCurso asignacion = new AsignacionCurso();
        asignacion.setDocente(docente);
        asignacion.setCurso(curso);
        asignacion.setHorasAsignadas(dto.getHorasAsignadas());

        asignacionRepo.save(asignacion);

        return mapToResponseDTO(asignacion);
    }

    public List<AsignacionCursoResponseDTO> listarAsignaciones() {
        return asignacionRepo.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private AsignacionCursoResponseDTO mapToResponseDTO(AsignacionCurso a) {
        AsignacionCursoResponseDTO dto = new AsignacionCursoResponseDTO();
        dto.setId(a.getId());
        dto.setNombreCurso(a.getCurso().getNombre());
        dto.setNombreDocente(a.getDocente().getNombre());
        dto.setHorasAsignadas(a.getHorasAsignadas());
        return dto;
    }
}