package sistema.academico.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "evaluacion")
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoEvaluacion tipo;
    private String descripcion;
    private LocalDate fechaCreacion;
    private LocalDate fechaLimite;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL)
    private List<Calificacion> calificaciones;
}
