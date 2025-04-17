package sistema.academico.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistema.academico.enums.EstadoMatricula;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaResponseDTO {
    private Long id;
    private LocalDate fechaMatricula;
    private LocalDate fechaCancelacion;
    private String motivoCancelacion;
    private EstadoMatricula estado;
    private EstudianteMatriculaDTO estudiante;
    private SemestreMatriculaDTO semestre;
    private ProgramaAcademicoMatriculaDTO programaAcademico;
}
