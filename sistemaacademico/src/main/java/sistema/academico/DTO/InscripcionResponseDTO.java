package sistema.academico.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionResponseDTO {
    private Long idInscripcion;
    private Long idCurso;
    private String nombreCurso;
    private String estadoInscripcion;
    private Double notaFinal;
    private String fechaInscripcion;
}
