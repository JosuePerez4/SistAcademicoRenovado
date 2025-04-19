package sistema.academico.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatricularCursoResponseDTO {
    private Long idInscripcion;
    private String nombreCurso;
    private String codigoCurso;
    private String nombreEstudiante;
    private String estadoInscripcion;
    private String fechaInscripcion;
}
