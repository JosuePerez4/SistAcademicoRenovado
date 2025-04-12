package sistema.academico.DTO;

import lombok.Data;

@Data
public class EstudianteResponseDTO {

    private Long id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String direccion;
    private String correo;
    private String telefono;
    private String genero;
    private String fechaNacimiento;
    private String codigo;
    private boolean estado;
    private String rol;
    private double promedio;
    private boolean beca;
    private String fechaIngreso;
    private String fechaEgreso;
}