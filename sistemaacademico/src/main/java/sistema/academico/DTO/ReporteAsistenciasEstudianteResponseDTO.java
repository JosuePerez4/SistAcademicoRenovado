package sistema.academico.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteAsistenciasEstudianteResponseDTO {
    private String nombreEstudiante;
    private Long cantidadAsistencias;
    private Long cantidadAsistenciasPresentes;
    private Long cantidadFaltas;
    private Long cantidadJustificadas;
    private Double porcentajeDeAsistencia;
}
