package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Calificacion;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Estudiante;
import sistema.academico.repository.CalificacionRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    // Registrar una calificación
    public Calificacion registrarCalificacion(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    // Actualizar calificación
    public Calificacion actualizarCalificacion(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    // Eliminar una calificación por ID
    public void eliminarCalificacion(Long id) {
        calificacionRepository.deleteById(id);
    }

    // Buscar calificaciones por estudiante
    public List<Calificacion> obtenerPorEstudiante(Estudiante estudiante) {
        return calificacionRepository.findByEstudiante(estudiante);
    }

    // Buscar calificaciones por curso
    public List<Calificacion> obtenerPorCurso(Curso curso) {
        return calificacionRepository.findByCurso(curso);
    }

    // Buscar calificaciones por fecha de registro
    public List<Calificacion> obtenerPorFechaRegistro(Date fechaRegistro) {
        return calificacionRepository.findByFechaRegistro(fechaRegistro);
    }

    // Buscar calificaciones por estudiante y curso
    public List<Calificacion> obtenerPorEstudianteYCurso(Estudiante estudiante, Curso curso) {
        return calificacionRepository.findByEstudianteAndCurso(estudiante, curso);
    }

    // Buscar calificaciones por tipo de evaluación
    public List<Calificacion> obtenerPorTipoEvaluacion(String tipoEvaluacion) {
        return calificacionRepository.findByTipoEvaluacion(tipoEvaluacion);
    }

    // Modificar nota de una calificación
    public void modificarNota(Long id, float nuevaNota) {
        Optional<Calificacion> calificacionOpt = calificacionRepository.findById(id);
        if (calificacionOpt.isPresent()) {
            Calificacion calificacion = calificacionOpt.get();
            calificacion.setNota(nuevaNota);
            calificacionRepository.save(calificacion);
        }
    }

    // Obtener detalles de una calificación
    public String obtenerDetalles(Long id) {
        Optional<Calificacion> calificacionOpt = calificacionRepository.findById(id);
        if (calificacionOpt.isPresent()) {
            Calificacion calificacion = calificacionOpt.get();
            return String.format("Calificación: %s, Nota: %.2f, Fecha de Registro: %s",
                    calificacion.getEvaluacion(), calificacion.getNota(), calificacion.getFechaRegistro());
        }
        return "Calificación no encontrada";
    }
}
