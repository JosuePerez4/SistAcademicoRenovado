package sistema.academico.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AsignarRolRequest {

    private Long usuarioId;
    private List<Long> rolIds;
}