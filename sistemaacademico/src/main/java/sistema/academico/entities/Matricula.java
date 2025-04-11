package sistema.academico.entities;

import jakarta.persistence.*;
import lombok.*;
import sistema.academico.enums.EstadoMatricula;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaMatricula;

    private LocalDate fechaCancelacion;

    private String motivoCancelacion;

    @Enumerated(EnumType.STRING)
    private EstadoMatricula estado;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "semestre_id")
    private Semestre semestre;

    @ManyToOne
    @JoinColumn(name = "programa_id")
    private ProgramaAcademico programa;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscripcion> inscripciones;
}