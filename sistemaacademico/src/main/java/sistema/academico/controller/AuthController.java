package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sistema.academico.DTO.ForgotPasswordRequest;
import sistema.academico.DTO.LoginRequest;
import sistema.academico.DTO.LoginResponse;
import sistema.academico.DTO.ResetPasswordRequest;
import sistema.academico.entities.PasswordResetToken;
import sistema.academico.entities.Usuario;
import sistema.academico.entities.VerificationToken;
import sistema.academico.repository.PasswordResetTokenRepository;
import sistema.academico.repository.UsuarioRepository;
import sistema.academico.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Asegúrate de tener este bean configurado en WebSecurityConfig

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Buscar el usuario por correo
            Usuario usuario = usuarioRepository.findByCorreo(loginRequest.getCorreo());
            if (usuario == null) {
                return ResponseEntity.badRequest().body("Usuario no encontrado");
            }

            // 2. Verificar la contraseña
            boolean passwordMatches = passwordEncoder.matches(
                loginRequest.getContrasena(),
                usuario.getContrasena()
            );

            if (!passwordMatches) {
                return ResponseEntity.badRequest().body("Contraseña incorrecta");
            }

            // 3. Si todo está bien, devolver la respuesta con los datos del usuario
            LoginResponse response = new LoginResponse(
                usuario.getId(),
                usuario.getNombre() + usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getRoles().stream()
                    .map(rol -> rol.getNombre())
                    .findFirst()
                    .orElse("Usuario")
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error en la autenticación: " + e.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    @Transactional
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String correo = forgotPasswordRequest.getCorreo();
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            return ResponseEntity.badRequest().body("No se encontró ningún usuario con este correo electrónico.");
        }

        // 1. Generar el token de restablecimiento de contraseña
        passwordResetTokenRepository.findByUsuario(usuario)
            .ifPresent(passwordResetTokenRepository::delete);

        PasswordResetToken passwordResetToken = new PasswordResetToken(usuario);
        passwordResetTokenRepository.save(passwordResetToken);

        // 2. Enviar el correo electrónico con el enlace que contiene el token
        String resetLink = frontendUrl + "/reset-password?token=" + passwordResetToken.getToken();
        String mensaje = String.format(
            "Hola %s,\n\n" +
            "Has solicitado restablecer tu contraseña. Haz clic en el siguiente enlace para continuar:\n" +
            "%s\n\n" +
            "Este enlace expirará en 24 horas.\n\n" +
            "Si no solicitaste este restablecimiento, puedes ignorar este correo.\n\n" +
            "Saludos,\nEquipo del Sistema Académico",
            usuario.getNombre(),
            resetLink
        );

        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(usuario.getCorreo());
            email.setSubject("Restablecimiento de Contraseña - Sistema Académico");
            email.setText(mensaje);
            mailSender.send(email);

            return ResponseEntity.ok("Se ha enviado un correo electrónico para restablecer su contraseña.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al enviar el correo electrónico. Por favor, intente más tarde.");
        }
    }

    @PostMapping("/reset-password")
    @Transactional
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetRequest) {
        return passwordResetTokenRepository.findByToken(resetRequest.getToken())
                .map(passwordResetToken -> {
                    if (passwordResetToken.getFechaExpiracion().isBefore(LocalDateTime.now())) {
                        passwordResetTokenRepository.delete(passwordResetToken);
                        return ResponseEntity.badRequest().body("El token de restablecimiento ha expirado.");
                    }

                    Usuario usuario = passwordResetToken.getUsuario();
                    // Encriptar la nueva contraseña antes de guardarla
                    usuario.setContrasena(passwordEncoder.encode(resetRequest.getNuevaContrasena()));
                    usuarioRepository.save(usuario);

                    passwordResetTokenRepository.delete(passwordResetToken);
                    return ResponseEntity.ok("Su contraseña ha sido restablecida exitosamente.");
                })
                .orElse(ResponseEntity.badRequest().body("Token de restablecimiento inválido."));
    }

    @GetMapping("/verify-email")
    @Transactional
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        return verificationTokenRepository.findByToken(token)
                .map(verificationToken -> {
                    if (verificationToken.getFechaExpiracion().isBefore(LocalDateTime.now())) {
                        verificationTokenRepository.delete(verificationToken);
                        return ResponseEntity.badRequest().body("El token de verificación ha expirado.");
                    }

                    Usuario usuario = verificationToken.getUsuario();
                    usuario.setEstado(true);
                    usuarioRepository.save(usuario);
                    verificationTokenRepository.delete(verificationToken);

                    return ResponseEntity
                            .ok("Su correo electrónico ha sido verificado exitosamente. Ahora puede iniciar sesión.");
                })
                .orElse(ResponseEntity.badRequest().body("Token de verificación inválido."));
    }
}