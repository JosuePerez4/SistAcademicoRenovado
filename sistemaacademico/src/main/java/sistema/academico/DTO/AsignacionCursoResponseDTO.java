package sistema.academico.DTO;

import lombok.Data;

@Data
public class AsignacionCursoResponseDTO {
    private Long id;
    private String nombreDocente;
    private String nombreCurso;
    private int horasAsignadas;
}