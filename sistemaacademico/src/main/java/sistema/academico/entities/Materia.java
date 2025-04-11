package sistema.academico.entities;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

// YO
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String codigo;
    private String descripcion;
    private int creditos;
    private Curso curso;
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
