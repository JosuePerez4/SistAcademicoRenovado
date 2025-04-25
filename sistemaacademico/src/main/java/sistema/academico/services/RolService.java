package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.academico.entities.Permiso;
import sistema.academico.entities.Rol;
import sistema.academico.entities.Usuario;
import sistema.academico.repository.PermisoRepository;
import sistema.academico.repository.RolRepository;
import sistema.academico.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Rol crearRol(String nombre) {
        Optional<Rol> existingRol = rolRepository.findByNombre(nombre);
        if (existingRol.isPresent()) {
            throw new IllegalStateException("Ya existe un rol con el nombre: " + nombre);
        }
        Rol nuevoRol = new Rol(nombre);
        return rolRepository.save(nuevoRol);
    }

    public Rol obtenerRolPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el rol con el nombre: " + nombre));
    }

    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }

    @Transactional
    public void asignarRolesAUsuario(Long usuarioId, List<Long> rolIds) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el usuario con ID: " + usuarioId));
        for (Long rolId : rolIds) {
            Rol rol = rolRepository.findById(rolId)
                    .orElseThrow(() -> new NoSuchElementException("No se encontró el rol con ID: " + rolId));
            usuario.agregarRol(rol);
        }
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void removerRolDeUsuario(Long usuarioId, Long rolId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el usuario con ID: " + usuarioId));
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el rol con ID: " + rolId));
        usuario.eliminarRol(rol);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void definirPermisosParaRol(Long rolId, List<Long> permisoIds) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el rol con ID: " + rolId));
        rol.getPermisos().clear(); // Limpiar los permisos existentes antes de asignar los nuevos
        for (Long permisoId : permisoIds) {
            Permiso permiso = permisoRepository.findById(permisoId)
                    .orElseThrow(() -> new NoSuchElementException("No se encontró el permiso con ID: " + permisoId));
            rol.agregarPermiso(permiso);
        }
        rolRepository.save(rol);
    }

    public List<Permiso> obtenerPermisosDeRol(Long rolId) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el rol con ID: " + rolId));
        return new ArrayList<>(rol.getPermisos());
    }

    public List<String> obtenerNombresDePermisosDeRol(Long rolId) {
        return obtenerPermisosDeRol(rolId).stream()
                .map(Permiso::getNombre)
                .collect(Collectors.toList());
    }

    public Rol obtenerRolPorId(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el rol con ID: " + id));
    }
}