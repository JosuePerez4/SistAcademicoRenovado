package sistema.academico.DTO;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObtenerAsistenciasEntreFechasRequestDTO {
    private Long inscripcionId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
