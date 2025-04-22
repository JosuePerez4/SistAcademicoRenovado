package sistema.academico.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sistema.academico.entities.Usuario;
import sistema.academico.entities.VerificationToken;
import sistema.academico.repository.UsuarioRepository;
import sistema.academico.repository.VerificationTokenRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Intentando autenticar al usuario con correo: " + username);
        Usuario usuario = usuarioRepository.findByCorreo(username);

        if (usuario == null) {
            System.out.println("Usuario no encontrado con el correo: " + username);
            throw new UsernameNotFoundException("Usuario no encontrado con el correo: " + username);
        }

        System.out.println("Usuario encontrado: " + usuario.getCorreo() + ", Estado: " + usuario.isEstado() + ", Rol: " + usuario.getRol());

        if (!usuario.isEstado()) {
            VerificationToken verificationToken = verificationTokenRepository.findByUsuario(usuario);
            if (verificationToken == null) {
                verificationToken = new VerificationToken(usuario);
                verificationTokenRepository.save(verificationToken);
                String verificationLink = "http://tu-aplicacion.com/verify-email?token=" + verificationToken.getToken();
                String mensaje = "Hola " + usuario.getNombre() + ",\n\n" +
                                 "Gracias por registrarte. Por favor, haz clic en el siguiente enlace para verificar tu correo electrónico:\n" +
                                 verificationLink + "\n\n" +
                                 "Este enlace expirará en 24 horas.\n\n" +
                                 "Si no te registraste, puedes ignorar este correo.";
                System.out.println("Simulando envío de correo electrónico de verificación a: " + usuario.getCorreo() + "\nContenido:\n" + mensaje);
            }
            throw new IllegalStateException("Su cuenta aún no ha sido verificada. Por favor, revise su correo electrónico.");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
        System.out.println("Authorities cargadas: " + authorities);

        return new User(usuario.getCorreo(), usuario.getContrasena(), authorities);
    }
}