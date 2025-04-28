package sistema.academico.DTO;

import lombok.*;
import sistema.academico.enums.EstadoCurso;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListarCursoPorMateriaDTO {
    private String nombre;
    private long materiaId;
    private String grupo;
    private int cupoMaximo;
    private long docenteId;
    private EstadoCurso estadoCurso;
}
