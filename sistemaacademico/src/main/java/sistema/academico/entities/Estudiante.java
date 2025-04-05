package sistema.academico.entities;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estudiantes")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float promedio;
    private boolean beca;
    private Date fechaIngreso;
    private Date fechaEgreso;

    @ManyToMany(mappedBy = "estudiantes")
    @JsonManagedReference
    private Set<Curso> cursos;

    @ManyToOne
    @JoinColumn(name = "programa_academico_id")
    private ProgramaAcademico programaAcademico;
}
