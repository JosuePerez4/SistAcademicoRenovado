package sistema.academico.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistema.academico.entities.Calificacion;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.Evaluacion;
import sistema.academico.entities.Inscripcion;
import sistema.academico.entities.Matricula;
import sistema.academico.entities.HistorialAcademico;
import sistema.academico.repository.CalificacionRepository;
import sistema.academico.repository.CursoRepository;
import sistema.academico.repository.EvaluacionRepository;
import sistema.academico.repository.InscripcionRepository;
import sistema.academico.repository.MatriculaRepository;
import sistema.academico.repository.HistorialAcademicoRepository;
import sistema.academico.repository.EstudianteRepository; // Asegúrate de tener esta importación

@Service
public class RegistroCalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private HistorialAcademicoRepository historialAcademicoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Transactional
    public boolean registrarCalificacion(Long idInscripcion, Long idEvaluacion, double nota) {
        Optional<Inscripcion> inscripcionOpt = inscripcionRepository.findById(idInscripcion);
        Optional<Evaluacion> evaluacionOpt = evaluacionRepository.findById(idEvaluacion);

        if (inscripcionOpt.isPresent() && evaluacionOpt.isPresent()) {
            Inscripcion inscripcion = inscripcionOpt.get();
            Evaluacion evaluacion = evaluacionOpt.get();

            Calificacion calificacion = new Calificacion();
            calificacion.setInscripcion(inscripcion);
            calificacion.setEvaluacion(evaluacion);
            calificacion.setNota(nota);

            calificacionRepository.save(calificacion);

            /* Corregir */


            // actualizarHistorialAcademico(inscripcion.getMatricula().getEstudiante().getId(), nota);

            return true;
        }
        return false;
    }

    /*
    @Transactional
    public boolean modificarCalificacion(Long idCalificacion, double nuevaNota) {
        Optional<Calificacion> calificacionOpt = calificacionRepository.findById(idCalificacion);
        if (calificacionOpt.isPresent()) {
            Calificacion calificacion = calificacionOpt.get();
            calificacion.setNota(nuevaNota);
            calificacionRepository.save(calificacion);

            actualizarHistorialAcademico(calificacion.getInscripcion().getMatricula().getEstudiante().getId(),
                    nuevaNota);

            return true;
        }
        return false;
    }*/

    public List<Calificacion> obtenerCalificacionesPorEstudiante(Long idEstudiante) {
        Optional<Estudiante> estudianteOpt = estudianteRepository.findById(idEstudiante);
        if (estudianteOpt.isPresent()) {
            Estudiante estudiante = estudianteOpt.get();
            // Obtener todas las matrículas del estudiante
            List<Matricula> matrículas = matriculaRepository.findByEstudianteId(estudiante.getId());
            List<Calificacion> calificaciones = new ArrayList<>();
            // Iterar sobre cada matrícula del estudiante
            for (Matricula matricula : matrículas) {
                // Obtener todas las inscripciones para esa matrícula
                List<Inscripcion> inscripciones = inscripcionRepository.findByMatriculaId(matricula.getId());
                // Iterar sobre cada inscripción y agregar sus calificaciones a la lista
                for (Inscripcion inscripcion : inscripciones) {
                    if (inscripcion.getCalificaciones() != null) {
                        calificaciones.addAll(inscripcion.getCalificaciones());
                    }
                }
            }
            return calificaciones;
        }
        return null;
    }

    public List<Calificacion> obtenerCalificacionesPorCurso(Long idCurso) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isPresent()) {
            Curso curso = cursoOpt.get();
            List<Inscripcion> inscripciones = inscripcionRepository.findByCursoId(curso.getId());
            List<Calificacion> calificaciones = new ArrayList<>();
            for (Inscripcion inscripcion : inscripciones) {
                if (inscripcion.getCalificaciones() != null) {
                    calificaciones.addAll(inscripcion.getCalificaciones());
                }
            }
            return calificaciones;
        }
        return null;
    }


    /* 
    Corregir el método, el historial académico no tiene calificaciones, sino que tiene una lista de cursos aprobados, reprobados y en proceso.
    private void actualizarHistorialAcademico(Long idEstudiante, double nuevaNota) {
        Optional<Estudiante> estudianteOpt = estudianteRepository.findById(idEstudiante);
        if (estudianteOpt.isPresent()) {
            Estudiante estudiante = estudianteOpt.get();
            List<HistorialAcademico> historialList = historialAcademicoRepository.findByEstudiante(estudiante);
            if (!historialList.isEmpty()) {
                HistorialAcademico historial = historialList.get(0);
                List<Calificacion> calificaciones = historial.getCalificaciones();
                if (calificaciones != null) {
                    // Recalcular el promedio general
                    double sumaNotas = 0;
                    for (Calificacion c : calificaciones) {
                        sumaNotas += c.getNota();
                    }
                    float promedio = (float) sumaNotas / calificaciones.size();
                    historial.setPromedioGeneral(promedio);
                    historialAcademicoRepository.save(historial);
                }
            }
        }
    }*/

    @Transactional
    public boolean eliminarCalificacion(Long idCalificacion) {
        Optional<Calificacion> calificacionOpt = calificacionRepository.findById(idCalificacion);
        if (calificacionOpt.isPresent()) {
            calificacionRepository.deleteById(idCalificacion);
            // Opcional: Considerar si se debe actualizar el historial académico al eliminar
            // una calificación
            return true;
        }
        return false;
    }
}