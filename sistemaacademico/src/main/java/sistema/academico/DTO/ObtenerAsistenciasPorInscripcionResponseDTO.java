package sistema.academico.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObtenerAsistenciasPorInscripcionResponseDTO {
    private String fecha;
    private String estado;
    private String justificacion;
}
