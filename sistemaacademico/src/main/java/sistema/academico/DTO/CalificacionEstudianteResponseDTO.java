package sistema.academico.DTO;

import lombok.Data;

@Data
public class CalificacionEstudianteResponseDTO {
    private Long idCalificacion;
    private double nota;
    private Long idCurso;
    private String nombreCurso;
    private String tipoEvaluacion;
    
}