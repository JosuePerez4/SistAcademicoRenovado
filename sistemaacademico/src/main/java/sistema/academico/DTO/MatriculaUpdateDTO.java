package sistema.academico.DTO;

import lombok.Data;
import sistema.academico.enums.EstadoMatricula;

import java.time.LocalDate;

@Data
public class MatriculaUpdateDTO {
    private LocalDate fechaCancelacion;
    private String motivoCancelacion;
    private EstadoMatricula estado;
}
