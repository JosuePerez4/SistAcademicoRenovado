package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.academico.entities.Permiso;
import sistema.academico.repository.PermisoRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public Permiso crearPermiso(String nombre) {
        Optional<Permiso> existingPermiso = permisoRepository.findByNombre(nombre);
        if (existingPermiso.isPresent()) {
            throw new IllegalStateException("Ya existe un permiso con el nombre: " + nombre);
        }
        Permiso nuevoPermiso = new Permiso(nombre);
        return permisoRepository.save(nuevoPermiso);
    }

    public Permiso obtenerPermisoPorNombre(String nombre) {
        return permisoRepository.findByNombre(nombre)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el permiso con el nombre: " + nombre));
    }

    public Permiso obtenerPermisoPorId(Long id) {
        return permisoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el permiso con ID: " + id));
    }

    public List<Permiso> obtenerTodosLosPermisos() {
        return permisoRepository.findAll();
    }
}