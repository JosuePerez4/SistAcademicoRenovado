package sistema.academico.DTO;

import lombok.Data;
import sistema.academico.entities.Evaluacion;
import sistema.academico.entities.Inscripcion;

@Data
public class CalificacionResponseDTO {
    private Long id;
    private double nota;
    private Inscripcion inscripcion;
    private Evaluacion evaluacion;
}