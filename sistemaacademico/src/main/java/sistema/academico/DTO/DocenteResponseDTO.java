package sistema.academico.DTO;

import lombok.Data;

@Data
public class DocenteResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String correo;
    private String especialidad;
    private int cargaHoraria;
    private String tituloProfesional;
    private String aniosExperiencia;
    private String tipoContrato;
}
