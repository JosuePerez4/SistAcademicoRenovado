package sistema.academico.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DefinirPermisosRolRequest {

    private Long rolId;
    private List<Long> permisoIds;
}