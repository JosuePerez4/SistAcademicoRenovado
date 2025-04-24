package sistema.academico.DTO;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialAcademicoDTO {
    private Long id;
    private Long estudianteId;
    private String nombreEstudiante;
    private float promedioGeneral;
    private int creditosAcumulados;
    private String estadoAcademicoActual;
    private int totalMateriasAprobadas;
    private int totalMateriasReprobadas;
    private List<CursoResponseDTO> cursosAprobados;
    private List<CursoResponseDTO> cursosReprobados;
    private List<CursoResponseDTO> cursosEnProceso;
}