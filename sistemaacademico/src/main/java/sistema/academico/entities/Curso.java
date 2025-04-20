package sistema.academico.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import sistema.academico.enums.EstadoCurso;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "grupo")
    private String grupo;
    private int cupoMaximo;
    @Column(name = "semestre")
    private int semestre;
    @Enumerated(EnumType.STRING)
    private EstadoCurso estadoCurso;
    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docente docente;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Horario> horarios;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Inscripcion> inscripciones;

}
