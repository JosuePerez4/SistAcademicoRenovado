package sistema.academico.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaPrimeraVezRequestDTO {
    private Long estudianteId;
    private Long semestreId;
    private Long programaId;
}
