package sistema.academico.DTO;

import lombok.*;
import sistema.academico.enums.EstadoCurso;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListarCursoPorMateriaDTO {
    private String nombre;
    private long materiaId;
    private String grupo;
    private int cupoMaximo;
    private Long docenteId;
    private EstadoCurso estadoCurso;
}
