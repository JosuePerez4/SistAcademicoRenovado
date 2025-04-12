package sistema.academico.DTO;

import lombok.Data;
import sistema.academico.enums.EstadoMatricula;

import java.time.LocalDate;
import java.util.List;

@Data
public class MatriculaResponseDTO {
    private Long id;
    private LocalDate fechaMatricula;
    private LocalDate fechaCancelacion;
    private String motivoCancelacion;
    private EstadoMatricula estado;
    private Long estudianteId;
    private Long semestreId;
    private Long programaId;
    private List<Long> inscripcionesIds;
}
