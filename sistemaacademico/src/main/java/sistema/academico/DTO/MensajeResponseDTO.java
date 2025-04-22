package sistema.academico.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class MensajeResponseDTO {
    private Long id;
    private Long remitenteId;
    private String remitenteNombreCompleto;
    private Long destinatarioId;
    private String destinatarioNombreCompleto;
    private String asunto;
    private String contenido;
    private Date fechaEnvio;
    private boolean leido;
}