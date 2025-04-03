package sistema.academico.entities;

import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Semestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String nombre;
    Date fechaInicio;
    Date fechaFin;
    List<Materia> materias;
}