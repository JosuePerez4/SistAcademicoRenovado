package sistema.academico.DTO;

import lombok.Data;

@Data
public class DocenteResponseDTO {
    private Long id;
    private String especialidad;
    private int cargaHoraria;
    private String tituloProfesional;
    private String aniosExperiencia;
    private String tipoContrato;
}
