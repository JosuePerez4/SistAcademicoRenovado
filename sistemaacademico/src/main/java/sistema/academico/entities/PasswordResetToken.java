package sistema.academico.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "password_reset_token")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime fechaExpiracion;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean usado;

    public PasswordResetToken(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
        this.fechaExpiracion = LocalDateTime.now().plusHours(24); // Token válido por 24 horas
        this.usado = false;
    }

    public PasswordResetToken(Usuario usuario) {
        this.token = java.util.UUID.randomUUID().toString();
        this.usuario = usuario;
        this.fechaExpiracion = LocalDateTime.now().plusHours(24);
        this.usado = false;
    }

    public boolean isTokenValido() {
        return !usado && LocalDateTime.now().isBefore(fechaExpiracion);
    }
}