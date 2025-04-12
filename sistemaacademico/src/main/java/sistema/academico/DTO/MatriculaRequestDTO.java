package sistema.academico.DTO;

import lombok.Data;
import sistema.academico.enums.EstadoMatricula;

import java.time.LocalDate;

@Data
public class MatriculaRequestDTO {
    private LocalDate fechaMatricula;
    private Long estudianteId;
    private Long semestreId;
    private Long programaId;
}
