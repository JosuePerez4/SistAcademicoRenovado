package sistema.academico.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarAsistenciaResponseDTO {
    private Long id;
    private Long inscripcionId;
    private String estado;
    private String justificacion;
}
