package sistema.academico.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermisoResponse {

    private Long id;
    private String nombre;

    public PermisoResponse(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public PermisoResponse() {
    }
}