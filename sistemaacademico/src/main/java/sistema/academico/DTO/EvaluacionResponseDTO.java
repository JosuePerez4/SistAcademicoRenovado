package sistema.academico.DTO;

import sistema.academico.enums.TipoEvaluacion;

import java.time.LocalDate;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionResponseDTO {
    private Long id;
    private TipoEvaluacion tipo;
    private String descripcion;
    private LocalDate fechaCreacion;
    private LocalDate fechaLimite;
    private String horarioAsignado;
}
