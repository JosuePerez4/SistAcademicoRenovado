package sistema.academico.DTO;

import lombok.Data;
import sistema.academico.enums.TipoEvaluacion;

import java.time.LocalDate;

@Data
public class EvaluacionResponseDTO {
    private Long id;
    private TipoEvaluacion tipo;
    private String descripcion;
    private LocalDate fechaCreacion;
    private LocalDate fechaLimite;
    private String horarioAsignado;
}
