package sistema.academico.entities;

import java.time.LocalTime;

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
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String aula;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
}
