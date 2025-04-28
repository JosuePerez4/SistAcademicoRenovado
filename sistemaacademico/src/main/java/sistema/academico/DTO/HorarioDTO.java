package sistema.academico.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HorarioDTO {
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String aula;
}
