package sistema.academico.entities;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;
// YO
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Usuario remitente;
    private Usuario destinatario;
    private String contenido;
    private Date fechaEnvio;
    private boolean leido;
}
