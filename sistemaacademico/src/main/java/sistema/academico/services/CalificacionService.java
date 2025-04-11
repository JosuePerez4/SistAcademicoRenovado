package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Calificacion;
import sistema.academico.repository.CalificacionRepository;

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
                    calificacion.getEvaluacion(), calificacion.getNota());
        }
        return "Calificación no encontrada";
    }
}
