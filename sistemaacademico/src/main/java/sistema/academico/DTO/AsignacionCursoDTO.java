package sistema.academico.DTO;

import lombok.Data;

@Data
public class AsignacionCursoDTO {
    private Long docenteId;
    private Long cursoId;
    private int horasAsignadas;
}
