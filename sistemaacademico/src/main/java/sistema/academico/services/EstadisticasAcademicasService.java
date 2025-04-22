package sistema.academico.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Inscripcion;
import sistema.academico.entities.Matricula;
import sistema.academico.repository.InscripcionRepository;
import sistema.academico.repository.MatriculaRepository;

@Service
public class EstadisticasAcademicasService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    // Umbral para considerar a un estudiante en riesgo por promedio
    private static final double UMBRAL_PROMEDIO_RIESGO = 3.0;

    // Número mínimo de cursos reprobados para considerar a un estudiante en riesgo
    private static final int MIN_CURSOS_REPROBADOS_RIESGO = 2;

    // Calcula el promedio académico general de un estudiante (CORREGIDO)
    public double calcularPromedioGeneralEstudiante(Long estudianteId) {
        List<Matricula> matriculas = matriculaRepository.findByEstudianteId(estudianteId);
        return matriculas.stream()
                .flatMap(matricula -> inscripcionRepository.findByMatriculaId(matricula.getId()).stream())
                .filter(inscripcion -> inscripcion.getNotaFinal() != null)
                .mapToDouble(Inscripcion::getNotaFinal) // Convertimos a DoubleStream
                .average()
                .orElse(0.0);
    }

    public long contarCursosAprobadosEstudiante(Long estudianteId) {
        List<Matricula> matriculas = matriculaRepository.findByEstudianteId(estudianteId);
        return matriculas.stream()
                .flatMap(matricula -> inscripcionRepository.findByMatriculaId(matricula.getId()).stream())
                .filter(inscripcion -> inscripcion.getNotaFinal() != null && inscripcion.getNotaFinal() >= 3.0) // Contamos notas mayores o iguales a 3.0
                .count();
    }

    public long contarCursosReprobadosEstudiante(Long estudianteId) {
        List<Matricula> matriculas = matriculaRepository.findByEstudianteId(estudianteId);
        return matriculas.stream()
                .flatMap(matricula -> inscripcionRepository.findByMatriculaId(matricula.getId()).stream())
                .filter(inscripcion -> (inscripcion.getEstado() != null && inscripcion.getEstado().toString().equals("REPROBADO")) || (inscripcion.getNotaFinal() != null && inscripcion.getNotaFinal() < 3.0))
                .count();
    }

    // Cuenta el número total de cursos inscritos por un estudiante (CORREGIDO)
    public long contarTotalCursosInscritosEstudiante(Long estudianteId) {
        List<Matricula> matriculas = matriculaRepository.findByEstudianteId(estudianteId);
        return matriculas.stream()
                .flatMap(matricula -> inscripcionRepository.findByMatriculaId(matricula.getId()).stream())
                .count();
    }

    // Proyecta el promedio para el próximo semestre (basado en el promedio general
    // actual)
    public double proyectarPromedioProximoSemestre(Long estudianteId) {
        return calcularPromedioGeneralEstudiante(estudianteId);
    }

    // Identifica si un estudiante está en riesgo académico
    public boolean estaEstudianteEnRiesgo(Long estudianteId) {
        double promedioGeneral = calcularPromedioGeneralEstudiante(estudianteId);
        long cursosReprobados = contarCursosReprobadosEstudiante(estudianteId);
        return promedioGeneral < UMBRAL_PROMEDIO_RIESGO || cursosReprobados >= MIN_CURSOS_REPROBADOS_RIESGO;
    }

    // Calcula el promedio académico de un estudiante para un semestre específico
    public double calcularPromedioPorSemestreEstudiante(Long estudianteId, Long semestreId) {
        // 1. Buscar la matrícula del estudiante para el semestre dado
        Matricula matricula = matriculaRepository.findByEstudianteIdAndSemestreId(estudianteId, semestreId)
                .orElse(null);

        if (matricula == null) {
            return 0.0; // El estudiante no tiene matrícula en este semestre
        }

        // 2. Obtener todas las inscripciones asociadas a esta matrícula
        List<Inscripcion> inscripciones = inscripcionRepository.findByMatriculaId(matricula.getId());

        // 3. Filtrar las inscripciones que tienen una nota final y convertirlas a
        // DoubleStream
        return inscripciones.stream()
                .filter(inscripcion -> inscripcion.getNotaFinal() != null)
                .mapToDouble(Inscripcion::getNotaFinal) // Convertimos a DoubleStream
                .average()
                .orElse(0.0);
    }
}