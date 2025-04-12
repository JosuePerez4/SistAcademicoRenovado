package sistema.academico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Inscripcion;
import sistema.academico.repository.InscripcionRepository;

@Service
public class EstadisticasAcademicasService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    // Calcula el promedio académico general de un estudiante
    public double calcularPromedioGeneralEstudiante(Long estudianteId) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByMatriculaId(estudianteId);
        return inscripciones.stream()
                .filter(inscripcion -> inscripcion.getNotaFinal() != null)
                .mapToDouble(Inscripcion::getNotaFinal)
                .average()
                .orElse(0.0);
    }

    // Cuenta el número total de cursos aprobados por un estudiante
    public long contarCursosAprobadosEstudiante(Long estudianteId) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByMatriculaId(estudianteId);
        return inscripciones.stream()
                .filter(inscripcion -> inscripcion.getEstado() != null && inscripcion.getEstado().toString().equals("APROBADO"))
                .count();
    }

    // Cuenta el número total de cursos reprobados por un estudiante
    public long contarCursosReprobadosEstudiante(Long estudianteId) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByMatriculaId(estudianteId);
        return inscripciones.stream()
                .filter(inscripcion -> inscripcion.getEstado() != null && inscripcion.getEstado().toString().equals("REPROBADO"))
                .count();
    }

    // Cuenta el número total de cursos inscritos por un estudiante
    public long contarTotalCursosInscritosEstudiante(Long estudianteId) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByMatriculaId(estudianteId);
        return inscripciones.size();
    }
}