package sistema.academico.DTO;

import sistema.academico.enums.TipoEspacio;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EspacioResponseDTO {
    private Long id;
    private String nombre;
    private TipoEspacio tipo;
    private Integer capacidad;
    private String ubicacion;
    private Boolean disponible;
}
