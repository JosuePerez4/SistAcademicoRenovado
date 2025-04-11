package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Asistencia;
import sistema.academico.entities.Clase;
import sistema.academico.entities.Horario;
import sistema.academico.repository.ClaseRepository;

import java.util.ArrayList;
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

    // Agregar una asistencia a la clase
    public Clase agregarAsistencia(Long idClase, Asistencia nuevaAsistencia) {
        Optional<Clase> claseOpt = claseRepository.findById(idClase);
        if (claseOpt.isPresent()) {
            Clase clase = claseOpt.get();
            nuevaAsistencia.setClase(clase); // Establece la relación
            clase.getAsistencias().add(nuevaAsistencia); // Agrega a la lista
            return claseRepository.save(clase); // Guarda clase con asistencias
        }
        return null;
    }
}
