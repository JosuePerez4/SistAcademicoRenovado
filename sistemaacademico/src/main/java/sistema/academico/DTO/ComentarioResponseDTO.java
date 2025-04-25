package sistema.academico.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ComentarioResponseDTO {
    private Long id;
    private String contenido;
    private String nombreAutor;
    private LocalDateTime fecha;
}
