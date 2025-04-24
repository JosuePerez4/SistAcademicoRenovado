package sistema.academico.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AsignacionCurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Docente docente;

    @ManyToOne
    private Curso curso;

    private int horasAsignadas;
    private String observaciones;
}
