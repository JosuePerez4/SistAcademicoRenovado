package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.academico.entities.Calificacion;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.HistorialAcademico;
import sistema.academico.repository.CalificacionRepository;
import sistema.academico.repository.EstudianteRepository;
import sistema.academico.repository.CursoRepository;
import sistema.academico.repository.HistorialAcademicoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RegistroCalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private HistorialAcademicoRepository historialAcademicoRepository;

    // Método para registrar calificaciones
    public boolean registrarCalificacion(Long idEstudiante, Long idCurso, float nota, String tipoEvaluacion) {
        Optional<Estudiante> estudianteOpt = estudianteRepository.findById(idEstudiante);
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);

        if (estudianteOpt.isPresent() && cursoOpt.isPresent()) {
            Estudiante estudiante = estudianteOpt.get();
            Curso curso = cursoOpt.get();

            // Validar que el estudiante esté inscrito en el curso
            if (!curso.getEstudiantes().contains(estudiante)) {
                return false; // El estudiante no está inscrito en el curso
            }

            // Crear la calificación
            Calificacion calificacion = new Calificacion();
            calificacion.setEstudiante(estudiante);
            calificacion.setCurso(curso);
            calificacion.setNota(nota);
            calificacion.setTipoEvaluacion(tipoEvaluacion);
            calificacion.setFechaRegistro(new Date());

            calificacionRepository.save(calificacion);

            // Actualizar el historial académico
            actualizarHistorialAcademico(estudiante, nota);

            return true; // Registro exitoso
        }
        return false; // No se encontró estudiante o curso
    }

    // Método para actualizar el historial académico
    private void actualizarHistorialAcademico(Estudiante estudiante, float nuevaNota) {
        Optional<HistorialAcademico> historialOpt = historialAcademicoRepository.findByEstudiante(estudiante);
        if (historialOpt.isPresent()) {
            HistorialAcademico historial = historialOpt.get();
            List<Calificacion> calificaciones = historial.getCalificaciones();

            if (calificaciones != null) {
                float sumaNotas = calificaciones.stream().mapToFloat(Calificacion::getNota).sum();
                float promedio = (sumaNotas + nuevaNota) / (calificaciones.size() + 1);
                historial.setPromedioGeneral(promedio);
                historialAcademicoRepository.save(historial);
            }
        }
    }
}
