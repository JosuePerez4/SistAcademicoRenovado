package sistema.academico.DTO;

import lombok.Data;

@Data
public class ComentarioRequestDTO {
    private Long autorId;
    private Long evaluacionId;
    private String contenido;
}
