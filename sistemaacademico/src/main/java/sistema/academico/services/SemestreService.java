package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Materia;
import sistema.academico.entities.Semestre;
import sistema.academico.repository.SemestreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SemestreService {

    @Autowired
    private SemestreRepository semestreRepository;

    // Crear un semestre
    public Semestre crearSemestre(Semestre semestre) {
        if (semestre.getMaterias() == null) {
            semestre.setMaterias(new ArrayList<>());
        }
        return semestreRepository.save(semestre);
    }

    // Modificar un semestre
    public Semestre modificarSemestre(Semestre semestre) {
        return semestreRepository.save(semestre);
    }

    // Eliminar un semestre por ID
    public void eliminarSemestre(Long idSemestre) {
        semestreRepository.deleteById(idSemestre);
    }

    // Listar materias de un semestre
    public List<Materia> listarMateriasSemestre(Long idSemestre) {
        Optional<Semestre> semestreOpt = semestreRepository.findById(idSemestre);
        return semestreOpt.map(Semestre::getMaterias).orElse(new ArrayList<>());
    }

    // Agregar una materia a un semestre
    public boolean agregarMateria(Long idSemestre, Materia nuevaMateria) {
        Optional<Semestre> semestreOpt = semestreRepository.findById(idSemestre);
        if (semestreOpt.isPresent()) {
            Semestre semestre = semestreOpt.get();
            if (semestre.getMaterias() == null) {
                semestre.setMaterias(new ArrayList<>());
            }
            semestre.getMaterias().add(nuevaMateria);
            semestreRepository.save(semestre);
            return true;
        }
        return false;
    }

    // Eliminar una materia de un semestre
    public boolean eliminarMateria(Long idSemestre, Materia materiaAEliminar) {
        Optional<Semestre> semestreOpt = semestreRepository.findById(idSemestre);
        if (semestreOpt.isPresent()) {
            Semestre semestre = semestreOpt.get();
            if (semestre.getMaterias() != null) {
                semestre.getMaterias().remove(materiaAEliminar);
                semestreRepository.save(semestre);
                return true;
            }
        }
        return false;
    }

    // Buscar un semestre por nombre
    public Semestre buscarPorNombre(String nombre) {
        return semestreRepository.findByNombre(nombre);
    }
}
