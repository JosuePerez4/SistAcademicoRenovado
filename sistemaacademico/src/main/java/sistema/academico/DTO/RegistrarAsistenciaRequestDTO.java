package sistema.academico.DTO;

import java.time.LocalDate;

import lombok.*;
import sistema.academico.enums.AsistenciaEstado;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarAsistenciaRequestDTO {
    private Long inscripcionId;
    private LocalDate fecha;
    private AsistenciaEstado estado;
    private String justificacion;
}
