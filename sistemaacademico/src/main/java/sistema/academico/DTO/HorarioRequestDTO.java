package sistema.academico.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HorarioRequestDTO {
    private Long cursoId;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private Long espacioId;
}
