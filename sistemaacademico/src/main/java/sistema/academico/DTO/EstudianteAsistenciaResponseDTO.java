package sistema.academico.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteAsistenciaResponseDTO {
    private String nombreEstudiante;
    private Long cantidadAsistencias;
}
