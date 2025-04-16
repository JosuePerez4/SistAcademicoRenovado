package sistema.academico.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistema.academico.enums.EstadoMatricula;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaEstadoDTO {
    private Long id;
    private String estudianteNombre;
    private EstadoMatricula estado;
    private LocalDate fechaMatricula;
    private String motivoCancelacion;
}
