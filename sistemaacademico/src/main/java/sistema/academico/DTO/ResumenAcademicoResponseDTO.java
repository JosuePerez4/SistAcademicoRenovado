package sistema.academico.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumenAcademicoResponseDTO {
        private String nombreEstudiante;
        private float promedioGeneral;
        private int creditosAcumulados;
        private int totalMateriasAprobadas;
        private int totalMateriasReprobadas;
        private int totalMateriasEnProceso;
        private String estadoAcademicoActual;
}
