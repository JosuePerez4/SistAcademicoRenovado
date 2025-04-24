package sistema.academico.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DocenteRegistroRequestDTO {
    // Datos personales
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

    // Datos profesionales
    private String especialidad;
    private int cargaHoraria;
    private String tituloProfesional;
    private String aniosExperiencia;
    private String tipoContrato;
}