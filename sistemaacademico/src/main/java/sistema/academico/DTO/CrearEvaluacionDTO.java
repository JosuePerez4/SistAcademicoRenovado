package sistema.academico.DTO;

import java.time.LocalDate;

import sistema.academico.enums.TipoEvaluacion;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearEvaluacionDTO {
    private TipoEvaluacion tipo;
    private String descripcion;
    private LocalDate fechaLimite;
    private Long cursoId;
}
