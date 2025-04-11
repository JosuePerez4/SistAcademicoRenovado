package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Asistencia;
import sistema.academico.entities.AsistenciaEstado;
import sistema.academico.entities.Estudiante;
import sistema.academico.repository.AsistenciaRepository;

import java.util.Date;
import java.util.List;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    // Registrar asistencia
    public Asistencia registrarAsistencia(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }

    // Obtener todas las asistencias
    public List<Asistencia> obtenerTodasAsistencias() {
        return asistenciaRepository.findAll();
    }

    // Buscar asistencias por estado
    public List<Asistencia> buscarPorEstado(AsistenciaEstado estado) {
        return asistenciaRepository.findByEstado(estado);
    }

    // Buscar asistencias por estudiante
    public List<Asistencia> buscarPorEstudiante(Estudiante estudiante) {
        return asistenciaRepository.findByEstudiante(estudiante);
    }

    // Buscar asistencias por fecha
    public List<Asistencia> buscarPorFecha(Date fecha) {
        return asistenciaRepository.findByFecha(fecha);
    }

    // Generar un reporte de inasistencias
    public String generarReporteInasistencias() {
        List<Asistencia> inasistencias = asistenciaRepository.findByEstado(AsistenciaEstado.AUSENTE);
        return "Reporte de Inasistencias: " + inasistencias.size() + " inasistencias registradas.";
    }

    // Generar un reporte de asistencias
    public String generarReporteAsistencias() {
        List<Asistencia> asistencias = asistenciaRepository.findByEstado(AsistenciaEstado.PRESENTE);
        return "Reporte de Asistencias: " + asistencias.size() + " asistencias registradas.";
    }
}
