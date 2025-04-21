package sistema.academico.DTO;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String token;
    private String nuevaContrasena;
}