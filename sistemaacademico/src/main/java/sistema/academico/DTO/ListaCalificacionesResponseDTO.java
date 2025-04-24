package sistema.academico.DTO;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaCalificacionesResponseDTO {
    private String nombreEstudiante;
    private String codigoEstudiante;
    private String nombreCurso;
    private String codigoCurso;
    private List<calificacionesPorEstudianteResponseDTO> calificaciones;
}
