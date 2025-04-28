package sistema.academico.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaEspacioDTO {
    private Long espacioId; 
    private LocalDate fecha;
    private LocalTime horaInicio; 
    private LocalTime horaFin;
    private String motivo;
    private Long cursoId;
}
