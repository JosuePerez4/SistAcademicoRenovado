package sistema.academico.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistema.academico.enums.EstadoMatricula;

@Schema(description = "DTO para actualizar el estado de una matrícula existente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarEstadoMatriculaRequestDTO {
    private Long matriculaId;
    private EstadoMatricula nuevoEstado;
}
