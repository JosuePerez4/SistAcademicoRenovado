package sistema.academico.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarEstadoMatriculaResponseDTO {
    private Long matriculaId;
    private String estadoAnterior;
    private String estadoActual;
    private LocalDate fechaActualizacion;
}
