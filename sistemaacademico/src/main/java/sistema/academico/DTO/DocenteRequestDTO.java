package sistema.academico.DTO;

import lombok.Data;

@Data
public class DocenteRequestDTO {
    private String especialidad;
    private int cargaHoraria;
    private String tituloProfesional;
    private String aniosExperiencia;
    private String tipoContrato;
}