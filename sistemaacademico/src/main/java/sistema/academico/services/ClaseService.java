package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Asistencia;
import sistema.academico.entities.Clase;
import sistema.academico.entities.Horario;
import sistema.academico.repository.ClaseRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    // Registrar una clase
    public Clase registrarClase(Clase clase) {
        return claseRepository.save(clase);
    }

    // Cancelar una clase por ID
    public void cancelarClase(Long id) {
        claseRepository.deleteById(id);
    }

    // Obtener todas las clases
    public List<Clase> obtenerTodasLasClases() {
        return claseRepository.findAll();
    }

    // Buscar clases por fecha
    public List<Clase> buscarClasesPorFecha(Date fecha) {
        return claseRepository.findByFecha(fecha);
    }

    // Buscar clases por horario
    public List<Clase> buscarClasesPorHorario(Horario horario) {
        return claseRepository.findByHorario(horario);
    }

    // Buscar clases por asistencia
    public List<Clase> buscarClasesPorAsistencia(Long asistenciaId) {
        return claseRepository.findByAsistencia_Id(asistenciaId);
    }

    // Buscar clases por fecha y horario
    public List<Clase> buscarClasesPorFechaYHorario(Date fecha, Horario horario) {
        return claseRepository.findByFechaAndHorario(fecha, horario);
    }

    // Actualizar horario de una clase
    public Clase actualizarHorario(Long idClase, Horario nuevoHorario) {
        Optional<Clase> claseOpt = claseRepository.findById(idClase);
        if (claseOpt.isPresent()) {
            Clase clase = claseOpt.get();
            clase.setHorario(nuevoHorario);
            return claseRepository.save(clase);
        }
        return null; // Si la clase no existe
    }

    // Agregar asistencia a una clase
    public Clase agregarAsistencia(Long idClase, Asistencia nuevaAsistencia) {
        Optional<Clase> claseOpt = claseRepository.findById(idClase);
        if (claseOpt.isPresent()) {
            Clase clase = claseOpt.get();
            clase.setAsistencia(nuevaAsistencia); // Vincular asistencia
            return claseRepository.save(clase);
        }
        return null; // Si la clase no existe
    }
}
