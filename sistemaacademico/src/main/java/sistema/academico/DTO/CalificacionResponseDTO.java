package sistema.academico.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistema.academico.entities.Evaluacion;
import sistema.academico.entities.Inscripcion;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalificacionResponseDTO {
    private Long id;
    private double nota;
    private Inscripcion inscripcion;
    private Evaluacion evaluacion;
}