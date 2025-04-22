package sistema.academico.DTO;

import lombok.Data;

@Data
public class EnviarMensajeRequestDTO {
    private Long remitenteId;
    private Long destinatarioId;
    private String asunto;
    private String contenido;
}
