package sistema.academico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Calificacion;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.HistorialAcademico;
import sistema.academico.repository.HistorialAcademicoRepository;

@Service
public class HistorialAcademicoService {
    @Autowired
    private HistorialAcademicoRepository historialAcademicoRepository;

    public List<HistorialAcademico> obtenerHistorialPorEstudiante(Estudiante estudiante) {
        return historialAcademicoRepository.findByEstudiante(estudiante);
    }

    // Agregar calificación al historial de un estudiante
    public void agregarCalificacion(String historialId, Calificacion calificacion) {
    }

    // Listar calificaciones de un historial
    public List<Calificacion> listarCalificaciones(String historialId) {
        return null;
    }

    // Método privado para recalcular el promedio y créditos
    private void recalcularPromedioYCreditos(HistorialAcademico historial) {
    }
}
