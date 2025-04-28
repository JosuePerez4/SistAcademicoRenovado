package sistema.academico.DTO;

import java.time.LocalDate;

import lombok.*;
import sistema.academico.enums.TipoEvaluacion;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearEvaluacionDTO {
    private TipoEvaluacion tipo;
    private String descripcion;
    private LocalDate fechaLimite;
    private Long cursoId;
}
