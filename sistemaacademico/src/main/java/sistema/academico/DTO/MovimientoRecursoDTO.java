package sistema.academico.DTO;

import lombok.*;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoRecursoDTO {
    private Long recursoId;
    private Long usuarioId;
    private String tipoMovimiento;
    private String observacion;
    private LocalDate fechaMovimiento;
}
