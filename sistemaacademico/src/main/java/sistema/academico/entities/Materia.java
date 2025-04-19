package sistema.academico.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "materia")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "creditos")
    private int creditos;

    @Column(name = "codigo", unique = true)
    private String codigo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "horas")
    private int horas;

    @ManyToOne
    @JoinColumn(name = "semestre_id")
    private Semestre semestre;

    @OneToMany(mappedBy = "materia")
    private List<Curso> cursos;

    @ManyToOne
    @JoinColumn(name = "programa_academico_id")
    private ProgramaAcademico programaAcademico;

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL)
    private List<Tema> temario;

    @ManyToMany
    @JoinTable(name = "materia_prerrequisitos",

            joinColumns = @JoinColumn(name = "materia_id"),

            inverseJoinColumns = @JoinColumn(name = "prerrequisito_id"))
    private List<Materia> prerrequisitos;

    @ManyToMany(mappedBy = "prerrequisitos")
    private List<Materia> materiasQueLaTienenComoPrerrequisito;
}
