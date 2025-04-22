package sistema.academico.DTO;

import lombok.Data;

@Data
public class CalificacionRequestDTO {
    private Long inscripcionId;
    private Long evaluacionId;
    private double nota;
}