package sistema.academico.DTO;

import java.sql.Time;
import java.time.LocalTime;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HorarioRequestDTO {
    private Long cursoId;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String aula;
}
