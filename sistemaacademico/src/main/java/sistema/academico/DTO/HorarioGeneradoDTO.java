package sistema.academico.DTO;



import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HorarioGeneradoDTO {
    private String nombreCurso;
    private String codigoCurso;
    private String nombreEspacio;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
}
