package sistema.academico.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemestreMatriculaDTO {
    private Long id;
    private int numero;
    private String nombre;
    private int anio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
