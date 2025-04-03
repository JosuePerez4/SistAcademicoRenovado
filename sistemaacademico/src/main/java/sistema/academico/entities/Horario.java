package sistema.academico.entities;

import java.sql.Time;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String diaSemana;
    private Time horaInicio;
    private Time horaFin;
    private String aula;
}

