package sistema.academico.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolResponse {

    private Long id;
    private String nombre;
    private List<String> permisos; // Lista de nombres de permisos asociados al rol

    public RolResponse(Long id, String nombre, List<String> permisos) {
        this.id = id;
        this.nombre = nombre;
        this.permisos = permisos;
    }

    public RolResponse() {
    }
}