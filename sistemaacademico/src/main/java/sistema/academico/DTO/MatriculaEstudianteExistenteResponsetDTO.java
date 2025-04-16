package sistema.academico.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaEstudianteExistenteResponsetDTO {
    private Long matriculaId;
    private Long estudianteId;
    private String nombreEstudiante;
    private Long semestreId;
    private String nombreSemestre;
    private String programaAcademico;
    private LocalDate fechaMatricula;
    private String estado;
}
