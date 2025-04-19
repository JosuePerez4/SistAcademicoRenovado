package sistema.academico.DTO;

import java.time.LocalDate;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteUpdateDTO {
    private String nombre;
    private String apellido;
    private String direccion;
    private String correo;
    private String telefono;
    private boolean estado;
    private boolean beca;
    private LocalDate fechaEgreso;
}
