package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.academico.entities.PasswordResetToken;
import sistema.academico.entities.Usuario;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUsuario(Usuario usuario);
}