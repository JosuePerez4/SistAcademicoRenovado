package sistema.academico.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoResponseDTO {
    private Long id;
    private String nombre;
    private String codigo;
    private int creditos;
}
