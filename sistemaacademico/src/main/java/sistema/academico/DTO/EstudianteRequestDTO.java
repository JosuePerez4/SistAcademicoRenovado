package sistema.academico.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EstudianteRequestDTO {
    private String cedula;
    private String nombre;
    private String apellido;
    private String direccion;
    private String correo;
    private String telefono;
    private String genero;
    private LocalDate fechaNacimiento;
    private String codigo;
    private String contrasena;
    private boolean estado;
    private String rol;
    private double promedio;
    private boolean beca;
    private LocalDate fechaEgreso;
    private Long programaAcademicoId;
}
