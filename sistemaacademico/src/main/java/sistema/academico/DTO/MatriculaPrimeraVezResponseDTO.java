package sistema.academico.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaPrimeraVezResponseDTO {
    private Long id;
    private String fechaMatricula;
    private String estado;
    private Long estudianteId;
    private Long semestreId;
    private Long programaId;
}
