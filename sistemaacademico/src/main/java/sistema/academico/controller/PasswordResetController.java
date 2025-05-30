package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.academico.services.UsuarioService;

import java.util.Map;

@RestController
@RequestMapping("/api/password")
@CrossOrigin(origins = "*")
public class PasswordResetController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/recuperar")
    public ResponseEntity<?> solicitarRecuperacion(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        if (correo == null || correo.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El correo es requerido");
        }
        
        String resultado = usuarioService.recuperarContrasena(correo);
        return ResponseEntity.ok(Map.of("mensaje", resultado));
    }

    @GetMapping("/validar-token/{token}")
    public ResponseEntity<?> validarToken(@PathVariable String token) {
        boolean esValido = usuarioService.validarTokenRecuperacion(token);
        return ResponseEntity.ok(Map.of("valido", esValido));
    }

    @PostMapping("/restablecer")
    public ResponseEntity<?> restablecerContrasena(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String nuevaContrasena = request.get("nuevaContrasena");

        if (token == null || nuevaContrasena == null) {
            return ResponseEntity.badRequest().body("Token y nueva contraseña son requeridos");
        }

        boolean resultado = usuarioService.restablecerContrasena(token, nuevaContrasena);
        if (resultado) {
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada exitosamente"));
        } else {
            return ResponseEntity.badRequest().body("Token inválido o expirado");
        }
    }
} 