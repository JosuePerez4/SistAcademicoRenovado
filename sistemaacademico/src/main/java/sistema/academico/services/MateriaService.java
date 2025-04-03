package sistema.academico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Materia;
import sistema.academico.repository.MateriaRepository;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    // Registrar una materia
    public Materia registrarMateria(Materia materia) {
        return materiaRepository.save(materia);
    }

    // Actualizar una materia
    public Materia actualizarMateria(Materia materia) {
        if (materiaRepository.existsById(materia.getId())) {
            return materiaRepository.save(materia);
        }
        throw new RuntimeException("Materia no encontrada");
    }

    // Eliminar una materia por ID
    public void eliminarMateria(Long id) {
        if (materiaRepository.existsById(id)) {
            materiaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Materia no encontrada");
        }
    }

    // Obtener una materia por ID
    public Optional<Materia> obtenerMateriaPorId(Long id) {
        return materiaRepository.findById(id);
    }

    // Obtener todas las materias
    public List<Materia> obtenerTodasLasMaterias() {
        return materiaRepository.findAll();
    }

    // Agregar un prerrequisito
    public boolean agregarPrerrequisito(Long idMateria, Materia prerrequisito) {
        Optional<Materia> materiaOpt = materiaRepository.findById(idMateria);
        if (materiaOpt.isPresent()) {
            Materia materia = materiaOpt.get();
            materia.getPrerrequisitos().add(prerrequisito);
            materiaRepository.save(materia);
            return true;
        }
        return false;
    }

    // Eliminar un prerrequisito
    public boolean eliminarPrerrequisito(Long idMateria, Materia prerrequisito) {
        Optional<Materia> materiaOpt = materiaRepository.findById(idMateria);
        if (materiaOpt.isPresent()) {
            Materia materia = materiaOpt.get();
            if (materia.getPrerrequisitos().remove(prerrequisito)) {
                materiaRepository.save(materia);
                return true;
            }
        }
        return false;
    }

    // Obtener prerrequisitos de una materia
    public List<Materia> obtenerPrerrequisitos(Long idMateria) {
        Optional<Materia> materiaOpt = materiaRepository.findById(idMateria);
        return materiaOpt.map(Materia::getPrerrequisitos)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
    }

    // Obtener temas de una materia
    public List<String> obtenerTemas(Long idMateria) {
        Optional<Materia> materiaOpt = materiaRepository.findById(idMateria);
        return materiaOpt.map(Materia::getTemario).orElseThrow(() -> new RuntimeException("Materia no encontrada"));
    }
}
