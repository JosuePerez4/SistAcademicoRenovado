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
    public Estudiante actualizarEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    // Eliminar un estudiante por ID
    public void eliminarEstudiante(long codigo) {
        estudianteRepository.deleteById(codigo);
    }

    // Obtener un estudiante por código
    public Optional<Estudiante> obtenerEstudiantePorCodigo(long codigo) {
        return estudianteRepository.findById(codigo);
    }

    // Obtener todos los estudiantes
    public List<Estudiante> obtenerTodos() {
        return estudianteRepository.findAll();
    }
}