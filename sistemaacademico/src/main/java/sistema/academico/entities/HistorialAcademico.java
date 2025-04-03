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
    Estudiante estudiante;
    List<Calificacion> calificaciones;
    List<Curso> cursosAprobados;
    List<Curso> cursosReprobados;
    float promedioGeneral;
    int creditosAcumulados;
}
