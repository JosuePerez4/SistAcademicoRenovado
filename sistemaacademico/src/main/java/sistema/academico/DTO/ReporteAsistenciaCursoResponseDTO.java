package sistema.academico.DTO;

import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteAsistenciaCursoResponseDTO {
    private String nombreCurso;
    private List<EstudianteAsistenciaResponseDTO> asistencias;
}
