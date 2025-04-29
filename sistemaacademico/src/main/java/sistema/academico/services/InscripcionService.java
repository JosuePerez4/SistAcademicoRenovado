package sistema.academico.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sistema.academico.DTO.EstadoInscripcionResponseDTO;
import sistema.academico.DTO.InscripcionResponseDTO;
import sistema.academico.DTO.InscripcionesPorMatriculaResponseDTO;
import sistema.academico.DTO.MatricularCursoResponseDTO;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Inscripcion;
import sistema.academico.entities.Matricula;
import sistema.academico.enums.EstadoInscripcion;
import sistema.academico.repository.CursoRepository;
import sistema.academico.repository.InscripcionRepository;
import sistema.academico.repository.MatriculaRepository;

@Service
public class InscripcionService {
    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public MatricularCursoResponseDTO inscribirEstudianteEnCurso(Long matriculaId, Long cursoId) {
        if (inscripcionRepository.existsByMatriculaIdAndCursoId(matriculaId, cursoId)) {
            throw new IllegalArgumentException("Ya está inscrito en este curso.");
        }

        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula no encontrada"));
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setMatricula(matricula);
        inscripcion.setCurso(curso);
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setEstado(EstadoInscripcion.INSCRITO);
        inscripcion.setEstudiante(matricula.getEstudiante());

        inscripcion = inscripcionRepository.save(inscripcion);

        MatricularCursoResponseDTO response = new MatricularCursoResponseDTO();
        response.setIdInscripcion(inscripcion.getId());
        response.setNombreCurso(curso.getNombre());
        response.setCodigoCurso(curso.getCodigo());
        response.setNombreEstudiante(
                matricula.getEstudiante().getNombre() + " " + matricula.getEstudiante().getApellido());
        response.setEstadoInscripcion(inscripcion.getEstado().name());
        response.setFechaInscripcion(inscripcion.getFechaInscripcion().toString());

        return response;
    }

    public MatricularCursoResponseDTO obtenerInscripcionPorId(Long id) {
        Optional<Inscripcion> inscripcionOpt = inscripcionRepository.findById(id);
        if (inscripcionOpt.isPresent()) {
            Inscripcion inscripcion = inscripcionOpt.get();
            MatricularCursoResponseDTO response = new MatricularCursoResponseDTO();
            response.setIdInscripcion(inscripcion.getId());
            response.setNombreCurso(inscripcion.getCurso().getNombre());
            response.setCodigoCurso(inscripcion.getCurso().getCodigo());
            response.setNombreEstudiante(inscripcion.getMatricula().getEstudiante().getNombre() + " "
                    + inscripcion.getMatricula().getEstudiante().getApellido());
            response.setEstadoInscripcion(inscripcion.getEstado().name());
            response.setFechaInscripcion(inscripcion.getFechaInscripcion().toString());
            return response;
        }
        return null;
    }

    public InscripcionesPorMatriculaResponseDTO listarInscripcionesPorMatricula(Long matriculaId) {
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula no encontrada"));
        List<Inscripcion> inscripciones = inscripcionRepository.findByMatriculaId(matriculaId);

        // Crear el objeto de respuesta principal
        InscripcionesPorMatriculaResponseDTO response = new InscripcionesPorMatriculaResponseDTO();
        response.setIdMatricula(matricula.getId());
        response.setEstudianteNombre(
                matricula.getEstudiante().getNombre() + " " + matricula.getEstudiante().getApellido());

        // Crear una lista para las inscripciones convertidas
        List<InscripcionResponseDTO> inscripcionesDTO = new ArrayList<>();

        // Recorrer las inscripciones y convertirlas
        for (Inscripcion inscripcion : inscripciones) {
            InscripcionResponseDTO inscripcionDTO = new InscripcionResponseDTO();
            inscripcionDTO.setIdInscripcion(inscripcion.getId());
            inscripcionDTO.setIdCurso(inscripcion.getCurso().getId());
            inscripcionDTO.setNombreCurso(inscripcion.getCurso().getNombre());
            inscripcionDTO.setEstadoInscripcion(inscripcion.getEstado().name());
            inscripcionDTO.setNotaFinal(inscripcion.getNotaFinal() != null ? inscripcion.getNotaFinal() : 0.0);
            inscripcionDTO.setFechaInscripcion(inscripcion.getFechaInscripcion().toString());

            inscripcionesDTO.add(inscripcionDTO);
        }

        // Asignar la lista de inscripciones convertidas al objeto de respuesta
        // principal
        response.setInscripciones(inscripcionesDTO);

        return response;
    }

    @Transactional
    public boolean eliminarInscripcion(Long inscripcionId) {
        Optional<Inscripcion> inscripcionOpt = inscripcionRepository.findById(inscripcionId);
        if (inscripcionOpt.isPresent()) {
            inscripcionRepository.deleteById(inscripcionId);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean cancelarInscripcion(Long inscripcionId) {
        Optional<Inscripcion> inscripcionOpt = inscripcionRepository.findById(inscripcionId);
        if (inscripcionOpt.isPresent()) {
            Inscripcion insc = inscripcionOpt.get();
            insc.setEstado(EstadoInscripcion.RETIRADO);
            inscripcionRepository.save(insc);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean registrarNotaFinal(Long inscripcionId, Double notaFinal) {
        Optional<Inscripcion> inscripcionOpt = inscripcionRepository.findById(inscripcionId);
        if (inscripcionOpt.isPresent()) {
            Inscripcion insc = inscripcionOpt.get();
            insc.setNotaFinal(notaFinal);
            inscripcionRepository.save(insc);
            return true;
        }
        return false;
    }

    public EstadoInscripcionResponseDTO estadoCurso(Long inscripcionId) {
        Optional<Inscripcion> inscripcionOpt = inscripcionRepository.findById(inscripcionId);
        if (inscripcionOpt.isPresent()) {
            Inscripcion inscripcion = inscripcionOpt.get();
            EstadoInscripcionResponseDTO response = new EstadoInscripcionResponseDTO();
            response.setEstadoInscripcion(inscripcion.getEstado().name());
            return response;
        }
        return null;
    }

    public long contarCursosAprobadosPorMatricula(Long matriculaId) {
        return inscripcionRepository.countByMatriculaIdAndEstado(matriculaId, EstadoInscripcion.APROBADO);
    }

    public boolean existeInscripcion(Long matriculaId, Long cursoId) {
        return inscripcionRepository.existsByMatriculaIdAndCursoId(matriculaId, cursoId);
    }

    public List<MatricularCursoResponseDTO> listarInscripcionesActivasPorCurso(Long cursoId) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByCursoIdAndEstado(cursoId,
                EstadoInscripcion.INSCRITO);
        List<MatricularCursoResponseDTO> responseList = new ArrayList<>();

        for (Inscripcion inscripcion : inscripciones) {
            MatricularCursoResponseDTO response = new MatricularCursoResponseDTO();
            response.setIdInscripcion(inscripcion.getId());
            response.setNombreCurso(inscripcion.getCurso().getNombre());
            response.setCodigoCurso(inscripcion.getCurso().getCodigo());
            response.setNombreEstudiante(inscripcion.getMatricula().getEstudiante().getNombre() + " "
                    + inscripcion.getMatricula().getEstudiante().getApellido());
            response.setEstadoInscripcion(inscripcion.getEstado().name());
            response.setFechaInscripcion(inscripcion.getFechaInscripcion().toString());

            responseList.add(response);
        }

        return responseList;
    }

    public int contarInscripcionesActivasPorCurso(Long cursoId) {
        return inscripcionRepository.findByCursoIdAndEstado(cursoId, EstadoInscripcion.INSCRITO).size();
    }

    public long contarInscritosEnCurso(Long cursoId) {
        return inscripcionRepository.countByCursoIdAndEstado(cursoId, EstadoInscripcion.INSCRITO);
    }

    public Double obtenerNotaFinal(Long matriculaId, Long cursoId) {
        return inscripcionRepository.findByMatriculaIdAndCursoId(matriculaId, cursoId)
                .map(Inscripcion::getNotaFinal)
                .orElse(null);
    }
}
