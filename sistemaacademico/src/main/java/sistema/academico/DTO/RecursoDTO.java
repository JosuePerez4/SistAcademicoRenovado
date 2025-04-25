package sistema.academico.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecursoDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private String descripcion;
    private boolean disponible;
    private String estado;
}
