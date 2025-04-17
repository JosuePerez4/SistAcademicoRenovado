package sistema.academico.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteMatriculaDTO {
    private String cedula;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String codigo;
}
