package sistema.academico.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

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
    private int cupoMaximo;
    private int semestre;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Horario> horarios;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Inscripcion> inscripciones;

    // Opcional:
    // private List<Clase> clases; // solo si registras sesiones individuales
}