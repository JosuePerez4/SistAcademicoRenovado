package sistema.academico.entities;
import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String programaAcademico;
    private float promedio;
    private boolean beca;
    private Date fechaIngreso;
    private Date fechaEgreso;
    private Curso curso;

}

