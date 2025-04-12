package sistema.academico.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema.academico.entities.Usuario;
import sistema.academico.services.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(usuario));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Usuario> actualizarUsuario(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(usuario));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        return usuarioService.eliminarUsuario(id) ? ResponseEntity.ok("Usuario eliminado correctamente.")
                                                  : ResponseEntity.badRequest().body("No se encontró el usuario.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> buscarUsuarioPorCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorCorreo(correo));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PostMapping("/login")
    public ResponseEntity<String> iniciarSesion(@RequestParam String correo, @RequestParam String contrasena) {
        return usuarioService.iniciarSesion(correo, contrasena) ? ResponseEntity.ok("Inicio de sesión exitoso.") 
                                                                : ResponseEntity.badRequest().body("Credenciales incorrectas o usuario inactivo.");
    }

    @GetMapping("/recuperar/{correo}")
    public ResponseEntity<String> recuperarContrasena(@PathVariable String correo) {
        return ResponseEntity.ok(usuarioService.recuperarContrasena(correo));
    }

    @PutMapping("/cambiar-contrasena")
    public ResponseEntity<String> cambiarContrasena(@RequestParam Long id, @RequestParam String actual, @RequestParam String nueva) {
        return usuarioService.cambiarContrasena(id, actual, nueva) ? ResponseEntity.ok("Contraseña actualizada correctamente.") 
                                                                   : ResponseEntity.badRequest().body("Error al cambiar la contraseña.");
    }

    @PostMapping("/cerrar-sesion/{id}")
    public ResponseEntity<String> cerrarSesion(@PathVariable Long id) {
        return usuarioService.cerrarSesion(id) ? ResponseEntity.ok("Sesión cerrada correctamente.") 
                                               : ResponseEntity.badRequest().body("Error al cerrar sesión.");
    }
}
