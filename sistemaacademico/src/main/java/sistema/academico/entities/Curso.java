package sistema.academico.entities;

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
    private Materia materia;
    private int cupoMaximo;
    private int semestre;
    private Clase clase;
    private Calificacion calificacion;
    private Estudiante estudiante;
    private Docente docente;
}
