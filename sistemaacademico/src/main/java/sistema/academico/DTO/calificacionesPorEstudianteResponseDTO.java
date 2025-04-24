package sistema.academico.DTO;

import java.time.LocalDate;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class calificacionesPorEstudianteResponseDTO {
    private Double nota;
    private String tipoEvaluacion;
    private String descripcionEvaluacion;
    private LocalDate fechaEvaluacion;
}
