package sistema.academico.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private String codigo;

    @ManyToOne
    private Materia materia; // Relación con Materia
    private int cupoMaximo;

    private int semestre;
    private Clase clase;
    private Calificacion calificacion;
    private Estudiante estudiante;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Clase> clases; // Relación con Clase (múltiples clases por curso)

    @ManyToMany
    @JoinTable(
        name = "curso_estudiante",
        joinColumns = @JoinColumn(name = "curso_id"),
        inverseJoinColumns = @JoinColumn(name = "estudiante_id")
    )
    private List<Estudiante> estudiantes; // Relación con múltiples estudiantes

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Calificacion> calificaciones; // Relación con Calificaciones
    
}
