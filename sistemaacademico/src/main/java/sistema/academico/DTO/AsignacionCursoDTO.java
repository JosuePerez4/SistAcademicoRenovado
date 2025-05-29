package sistema.academico.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionCursoDTO {
    private Long docenteId;
    private Long cursoId;
    private int horasAsignadas;
}
