package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.DTO.LoginResponse;
import sistema.academico.entities.Usuario;
import sistema.academico.entities.PasswordResetToken;
import sistema.academico.repository.UsuarioRepository;
import sistema.academico.repository.PasswordResetTokenRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private NotificacionService notificacionService;

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
    public LoginResponse iniciarSesion(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario != null && usuario.getContrasena().equals(contrasena) && usuario.isEstado()) {
            return new LoginResponse(usuario.getNombre()+usuario.getApellido(),usuario.getCorreo(), usuario.getRoles().stream()
                    .map(rol -> rol.getNombre())
                    .findFirst()
                    .orElse("Usuario"));
        }
        return null;
    }

    // Recuperar contraseña
    public String recuperarContrasena(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario != null) {
            // Verificar si ya existe un token activo
            Optional<PasswordResetToken> tokenExistente = passwordResetTokenRepository.findByUsuarioAndUsadoFalse(usuario);
            if (tokenExistente.isPresent()) {
                // Invalidar token anterior
                PasswordResetToken oldToken = tokenExistente.get();
                oldToken.setUsado(true);
                passwordResetTokenRepository.save(oldToken);
            }

            // Crear nuevo token
            String token = UUID.randomUUID().toString();
            PasswordResetToken passwordResetToken = new PasswordResetToken(token, usuario);
            passwordResetTokenRepository.save(passwordResetToken);

            // Enviar correo con el enlace de recuperación
            String asunto = "Recuperación de Contraseña - Sistema Académico";
            String mensaje = String.format(
                "Estimado/a %s,\n\n" +
                "Has solicitado restablecer tu contraseña. Por favor, utiliza el siguiente enlace para crear una nueva contraseña:\n\n" +
                "http://localhost:4200/reset-password?token=%s\n\n" +
                "Este enlace será válido por las próximas 24 horas.\n\n" +
                "Si no solicitaste este cambio, por favor ignora este correo.\n\n" +
                "Saludos,\nEquipo del Sistema Académico",
                usuario.getNombre(),
                token
            );

            notificacionService.enviarCorreo(usuario.getCorreo(), asunto, mensaje);
            return "Se han enviado las instrucciones para recuperar la contraseña a tu correo electrónico.";
        }
        return "Correo no encontrado.";
    }

    // Validar token de recuperación de contraseña
    public boolean validarTokenRecuperacion(String token) {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(token);
        return passwordResetToken.map(PasswordResetToken::isTokenValido).orElse(false);
    }

    // Restablecer contraseña usando token
    public boolean restablecerContrasena(String token, String nuevaContrasena) {
        Optional<PasswordResetToken> passwordResetTokenOpt = passwordResetTokenRepository.findByToken(token);
        if (passwordResetTokenOpt.isPresent()) {
            PasswordResetToken resetToken = passwordResetTokenOpt.get();
            if (resetToken.isTokenValido()) {
                Usuario usuario = resetToken.getUsuario();
                usuario.setContrasena(nuevaContrasena);
                usuarioRepository.save(usuario);

                // Marcar token como usado
                resetToken.setUsado(true);
                passwordResetTokenRepository.save(resetToken);
                return true;
            }
        }
        return false;
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
