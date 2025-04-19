package sistema.academico.DTO;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionesPorMatriculaResponseDTO {
    private long idMatricula;
    private String estudianteNombre;
    private List<InscripcionResponseDTO> inscripciones;
}
