package sistema.academico.DTO;

import lombok.Data;

@Data
public class CalificacionCursoResponseDTO {
    private Long idCalificacion;
    private double nota;
    private Long idEstudiante;
    private String nombreCompletoEstudiante;
    private String tipoEvaluacion;
}