package sistema.academico.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionCursoResponseDTO {
    private Long id;
    private String nombreDocente;
    private String nombreCurso;
    private int horasAsignadas;
}