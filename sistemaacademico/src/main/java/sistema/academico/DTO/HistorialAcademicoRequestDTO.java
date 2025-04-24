package sistema.academico.DTO;

import lombok.AllArgsConstructor;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialAcademicoRequestDTO {
    private long estudianteId;
    private long matriculaId;
}
