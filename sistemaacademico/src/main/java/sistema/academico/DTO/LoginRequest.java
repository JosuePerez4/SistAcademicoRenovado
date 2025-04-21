package sistema.academico.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String contrasena;
}