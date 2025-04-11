package sistema.academico.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sistema.academico.entities.Curso;
import sistema.academico.entities.EstadoInscripcion;
import sistema.academico.entities.Inscripcion;
import sistema.academico.entities.Matricula;
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
    public Inscripcion inscribirEstudianteEnCurso(Long matriculaId, Long cursoId) {
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

        return inscripcionRepository.save(inscripcion);
    }

    public Optional<Inscripcion> obtenerInscripcionPorId(Long id) {
        return inscripcionRepository.findById(id);
    }

    public List<Inscripcion> listarInscripcionesPorMatricula(Long matriculaId) {
        return inscripcionRepository.findByMatriculaId(matriculaId);
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
    public boolean cancelarInscripcion(Long inscripcionId, String motivo) {
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

    public boolean cursoAprobado(Long inscripcionId) {
        return inscripcionRepository.findById(inscripcionId)
                .map(insc -> insc.getEstado() == EstadoInscripcion.APROBADO)
                .orElse(false);
    }

    public long contarCursosAprobadosPorMatricula(Long matriculaId) {
        return inscripcionRepository.countByMatriculaIdAndEstado(matriculaId, EstadoInscripcion.APROBADO);
    }

    public List<Curso> obtenerCursosPorMatricula(Long matriculaId) {
        return inscripcionRepository.findByMatriculaId(matriculaId).stream()
                .map(Inscripcion::getCurso)
                .toList();
    }

    public boolean existeInscripcion(Long matriculaId, Long cursoId) {
        return inscripcionRepository.existsByMatriculaIdAndCursoId(matriculaId, cursoId);
    }

    public List<Inscripcion> listarInscripcionesActivasPorCurso(Long cursoId) {
        return inscripcionRepository.findByCursoIdAndEstado(cursoId, EstadoInscripcion.INSCRITO);
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
