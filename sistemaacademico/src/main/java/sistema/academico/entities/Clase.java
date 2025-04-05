package sistema.academico.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fecha;

    @ManyToOne
    private Horario horario; // Relación con Horario

    @ManyToOne
    private Curso curso; // Relación con Curso
    
    private Asistencia asistencia;
}
