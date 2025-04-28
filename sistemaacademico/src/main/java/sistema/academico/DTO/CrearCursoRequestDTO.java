package sistema.academico.DTO;

import sistema.academico.enums.EstadoCurso;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearCursoRequestDTO {
    private String nombre;
    private String descripcion;
    private String codigo;
    private EstadoCurso estadoCurso; // stringEnum: "activo", "inactivo"
    private String grupo;
    private int cupoMaximo;
    private int semestre;
    private Long docenteId;
    private Long materiaId;
}
