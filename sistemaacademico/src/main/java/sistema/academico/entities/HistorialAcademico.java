package sistema.academico.entities;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialAcademico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Estudiante estudiante;
    private List<Calificacion> calificaciones;
    private List<Curso> cursosAprobados;
    private List<Curso> cursosReprobados;
    private float promedioGeneral;
    private int creditosAcumulados;
}
