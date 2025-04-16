package sistema.academico.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistema.academico.enums.EstadoMatricula;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaResumenEstudianteDTO {
    private Long id;
    private String nombreEstudiante;
    private String codigoEstudiante;
    private EstadoMatricula estado;
    private int numeroSemestre;
    private String nombreSemestre;
}
