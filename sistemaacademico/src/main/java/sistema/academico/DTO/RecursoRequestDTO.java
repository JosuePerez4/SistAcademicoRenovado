package sistema.academico.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecursoRequestDTO {
    private String nombre;
    private String tipo;
    private String descripcion;
    private String estado;
}
