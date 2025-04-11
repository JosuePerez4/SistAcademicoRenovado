package sistema.academico.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estudiante")
public class Estudiante extends Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float promedio;
    private boolean beca;
    private Date fechaIngreso;
    private Date fechaEgreso;

    @ManyToOne
    @JoinColumn(name = "programa_academico_id")
    private ProgramaAcademico programaAcademico;
}
