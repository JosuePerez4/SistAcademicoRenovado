package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Usuario;
import sistema.academico.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registrar un nuevo usuario
    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Actualizar un usuario
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Eliminar un usuario por ID
    public boolean eliminarUsuario(Long idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (usuarioOpt.isPresent()) {
            usuarioRepository.deleteById(idUsuario);
            return true;
        }
        return false;
    }

    // Buscar un usuario por ID
    public Usuario buscarUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }

    // Buscar un usuario por correo
    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    // Buscar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Iniciar sesión
    public boolean iniciarSesion(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        return usuario != null && usuario.getContrasena().equals(contrasena) && usuario.isEstado();
    }

    // Recuperar contraseña (simulación)
    public String recuperarContrasena(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario != null) {
            return "Instrucciones para recuperar la contraseña enviadas al correo: " + correo;
        }
        return "Correo no encontrado.";
    }

    // Cambiar contraseña
    public boolean cambiarContrasena(Long idUsuario, String contrasenaActual, String nuevaContrasena) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getContrasena().equals(contrasenaActual)) {
                usuario.setContrasena(nuevaContrasena);
                usuarioRepository.save(usuario);
                return true;
            }
        }
        return false;
    }

    // Cerrar sesión (simulación)
    public boolean cerrarSesion(Long idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Suponiendo que el atributo estado controla sesiones activas/inactivas
            usuario.setEstado(false);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
}
