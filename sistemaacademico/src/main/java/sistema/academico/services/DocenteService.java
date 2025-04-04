package sistema.academico.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import sistema.academico.entities.Docente;
import sistema.academico.repository.DocenteRepository;
import java.util.*;

@Service
public class DocenteService {
    
    @Autowired
    private DocenteRepository docenteRepository;

     // Registrar Docente
    public Docente registrarDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    // Actualizar Docente
    public Docente actualizarDocente(Docente docente) {
        if (docenteRepository.existsById(docente.getId())) {
            return docenteRepository.save(docente);
        }
        throw new RuntimeException("Docente no encontrado");
    }

    // Eliminar Docente
    public void eliminarDocente(Long id) {
        if (docenteRepository.existsById(id)) {
            docenteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Docente no encontrado");
        }
    }

    /*  Crear Evaluación ---- Dudando si este método va aquí, ya que no es responsabilidad de la clase docente crear esa evaluación
    public void crearEvaluacion(Evaluacion evaluacion) {
        // Aquí podrías usar un repositorio para evaluaciones si lo tienes
        System.out.println("Evaluación creada: " + evaluacion.getTitulo());
    }*/

    // Obtener un Docente por ID
    public Optional<Docente> obtenerDocentePorId(Long id) {
        return docenteRepository.findById(id);
    }

    // Obtener todos los docentes
public List<Docente> obtenerTodosLosDocentes() {
    return docenteRepository.findAll();
}
}
