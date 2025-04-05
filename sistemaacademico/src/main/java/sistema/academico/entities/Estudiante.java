package sistema.academico.entities;
import java.util.Date;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante extends Usuario {

    private String programaAcademico;
    private float promedio;
    private boolean beca;

    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;

    @Temporal(TemporalType.DATE)
    private Date fechaEgreso;

    @ManyToMany(mappedBy = "estudiantes")
    private List<Curso> cursos;
}


