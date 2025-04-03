package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Estudiante;
import sistema.academico.repository.EstudianteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    // Registrar un estudiante
    public Estudiante registrarEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    // Actualizar un estudiante
    public Estudiante actualizarEstudiante(String codigo, Estudiante estudianteActualizado) {
        Optional<Estudiante> optionalEStudiante = estudianteRepository.findByCodigo(codigo);

        if (optionalEStudiante.isPresent()) {
            Estudiante estudiante = optionalEStudiante.get();

            estudiante.setBeca(estudianteActualizado.isBeca());
            estudiante.setCurso(estudianteActualizado.getCurso());
            estudiante.setFechaEgreso(estudianteActualizado.getFechaEgreso());
            estudiante.setFechaIngreso(estudianteActualizado.getFechaIngreso());
            estudiante.setProgramaAcademico(estudianteActualizado.getProgramaAcademico());
            estudiante.setPromedio(estudianteActualizado.getPromedio());
            // Guardamos los datos
            return estudianteRepository.save(estudiante);
        } else {
            throw new RuntimeException("No existe un estudiante con el código: " + codigo);
        }
    }

    // Eliminar un estudiante por ID
    public boolean eliminarEstudiante(long id) {
        if (obtenerEstudiantePorId(id).isPresent()) {
            estudianteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Obtener un estudiante por id
    public Optional<Estudiante> obtenerEstudiantePorId(long id) {
        return estudianteRepository.findById(id);
    }

    // Obtener todos los estudiantes
    public List<Estudiante> obtenerTodos() {
        return estudianteRepository.findAll();
    }
}