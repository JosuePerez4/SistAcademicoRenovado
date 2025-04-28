package sistema.academico.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import sistema.academico.enums.EstadoInscripcion;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inscripciones", uniqueConstraints = @UniqueConstraint(columnNames = { "matricula_id", "curso_id" }))
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_id")
    private Matricula matricula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private LocalDate fechaInscripcion;

    @Enumerated(EnumType.STRING)
    private EstadoInscripcion estado;

    private Double notaFinal;

    @OneToMany(mappedBy = "inscripcion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calificacion> calificaciones;

    @OneToMany(mappedBy = "inscripcion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asistencia> asistencias;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;
}
