package sistema.academico.entities;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "historial_academico")
public class HistorialAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @OneToOne
    @JoinColumn(name = "matricula_id")
    private Matricula matricula;

    @ManyToMany
    @JoinTable(name = "historial_cursos_aprobados", joinColumns = @JoinColumn(name = "historial_id"), inverseJoinColumns = @JoinColumn(name = "curso_id"))
    private List<Curso> cursosAprobados;

    @ManyToMany
    @JoinTable(name = "historial_cursos_reprobados", joinColumns = @JoinColumn(name = "historial_id"), inverseJoinColumns = @JoinColumn(name = "curso_id"))
    private List<Curso> cursosReprobados;

    @ManyToMany
    @JoinTable(name = "historial_cursos_en_proceso", joinColumns = @JoinColumn(name = "historial_id"), inverseJoinColumns = @JoinColumn(name = "curso_id"))
    private List<Curso> cursosEnProceso;

    @Column(name = "promedio_general")
    private float promedioGeneral;

    @Column(name = "creditos_acumulados")
    private int creditosAcumulados;

    @Column(name = "estado_academico_actual")
    private String estadoAcademicoActual;

    @Column(name = "total_materias_aprobadas")
    private int totalMateriasAprobadas;

    @Column(name = "total_materias_reprobadas")
    private int totalMateriasReprobadas;

    @Column(name = "total_materias_en_proceso")
    private int TotalMateriasEnProceso;
}
