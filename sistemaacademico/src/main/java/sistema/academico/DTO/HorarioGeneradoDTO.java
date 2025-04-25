package sistema.academico.DTO;

import java.sql.Time;
import java.time.LocalTime;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HorarioGeneradoDTO {
    private String nombreCurso;
    private String codigoCurso;
    private String aula;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
