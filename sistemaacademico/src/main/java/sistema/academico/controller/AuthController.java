package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sistema.academico.DTO.ForgotPasswordRequest;
import sistema.academico.DTO.LoginRequest;
import sistema.academico.DTO.ResetPasswordRequest;
import sistema.academico.entities.PasswordResetToken;
import sistema.academico.entities.Usuario;
import sistema.academico.entities.VerificationToken;
import sistema.academico.repository.PasswordResetTokenRepository;
import sistema.academico.repository.UsuarioRepository;
import sistema.academico.repository.VerificationTokenRepository;

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

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getCorreo(),
                        loginRequest.getContrasena()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("Inicio de sesión exitoso.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String correo = forgotPasswordRequest.getCorreo();
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            return ResponseEntity.badRequest().body("No se encontró ningún usuario con este correo electrónico.");
        }

        // 1. Generar el token de restablecimiento de contraseña
        PasswordResetToken existingToken = passwordResetTokenRepository.findByUsuario(usuario);
        if (existingToken != null) {
            passwordResetTokenRepository.delete(existingToken); // Eliminar token existente si hay uno
        }
        PasswordResetToken passwordResetToken = new PasswordResetToken(usuario);
        passwordResetTokenRepository.save(passwordResetToken);

        // 2. Enviar el correo electrónico con el enlace que contiene el token
        String resetLink = "http://tu-aplicacion.com/reset-password?token=" + passwordResetToken.getToken();
        String mensaje = "Hola " + usuario.getNombre() + ",\n\n" +
                "Has solicitado restablecer tu contraseña. Haz clic en el siguiente enlace para continuar:\n" +
                resetLink + "\n\n" +
                "Este enlace expirará en 24 horas.\n\n" +
                "Si no solicitaste este restablecimiento, puedes ignorar este correo.";

        System.out.println(
                "Simulando envío de correo electrónico a: " + usuario.getCorreo() + "\nContenido:\n" + mensaje);

        return ResponseEntity.ok("Se ha enviado un correo electrónico para restablecer su contraseña.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetRequest) {
        String token = resetRequest.getToken();
        String nuevaContrasena = resetRequest.getNuevaContrasena();

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken == null) {
            return ResponseEntity.badRequest().body("Token de restablecimiento inválido.");
        }

        if (passwordResetToken.getExpiryDate().before(new Date())) {
            passwordResetTokenRepository.delete(passwordResetToken); // Eliminar token expirado
            return ResponseEntity.badRequest().body("El token de restablecimiento ha expirado.");
        }

        Usuario usuario = passwordResetToken.getUsuario();
        usuario.setContrasena(nuevaContrasena); // Guardar la contraseña directamente (¡INSEGURO PARA PRODUCCIÓN!)
        usuarioRepository.save(usuario);

        passwordResetTokenRepository.delete(passwordResetToken); // Eliminar el token utilizado
        return ResponseEntity.ok("Su contraseña ha sido restablecida exitosamente.");
    }

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            return ResponseEntity.badRequest().body("Token de verificación inválido.");
        }

        if (verificationToken.getExpiryDate().before(new Date())) {
            verificationTokenRepository.delete(verificationToken);
            return ResponseEntity.badRequest().body("El token de verificación ha expirado.");
        }

        Usuario usuario = verificationToken.getUsuario();
        usuario.setEstado(true); // Marcar al usuario como verificado/activo
        usuarioRepository.save(usuario);
        verificationTokenRepository.delete(verificationToken); // Eliminar el token utilizado

        return ResponseEntity.ok("Su correo electrónico ha sido verificado exitosamente. Ahora puede iniciar sesión.");
    }
}