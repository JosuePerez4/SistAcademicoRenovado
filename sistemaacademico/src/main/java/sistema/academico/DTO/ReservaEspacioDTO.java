package sistema.academico.DTO;

import java.time.LocalDate;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaEspacioDTO {
    private Long espacioId; 
    private LocalDate fecha;
    private String horaInicio; 
    private String horaFin;
    private String motivo;
    private Long cursoId;
}
