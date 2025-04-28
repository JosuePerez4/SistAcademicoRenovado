package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistema.academico.DTO.AsignarRolRequest;
import sistema.academico.DTO.DefinirPermisosRolRequest;
import sistema.academico.DTO.RolResponse;
import sistema.academico.entities.Rol;
import sistema.academico.services.RolService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin") // Generalmente restringimos la gestión de roles y permisos a administradores
public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping("/roles")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<RolResponse> crearRol(@RequestBody String nombre) {
        Rol nuevoRol = rolService.crearRol(nombre);
        List<String> permisos = rolService.obtenerNombresDePermisosDeRol(nuevoRol.getId());
        return new ResponseEntity<>(new RolResponse(nuevoRol.getId(), nuevoRol.getNombre(), permisos), HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<RolResponse>> obtenerTodosLosRoles() {
        List<Rol> roles = rolService.obtenerTodosLosRoles();
        List<RolResponse> rolResponses = roles.stream()
                .map(rol -> new RolResponse(rol.getId(), rol.getNombre(), rolService.obtenerNombresDePermisosDeRol(rol.getId())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(rolResponses);
    }

    @PostMapping("/usuarios/roles")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<String> asignarRolesAUsuario(@RequestBody AsignarRolRequest asignarRolRequest) {
        rolService.asignarRolesAUsuario(asignarRolRequest.getUsuarioId(), asignarRolRequest.getRolIds());
        return ResponseEntity.ok("Roles asignados al usuario exitosamente.");
    }

    @DeleteMapping("/usuarios/{usuarioId}/roles/{rolId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<String> removerRolDeUsuario(@PathVariable Long usuarioId, @PathVariable Long rolId) {
        rolService.removerRolDeUsuario(usuarioId, rolId);
        return ResponseEntity.ok("Rol removido del usuario exitosamente.");
    }

    @PostMapping("/roles/{rolId}/permisos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<String> definirPermisosParaRol(@PathVariable Long rolId, @RequestBody DefinirPermisosRolRequest definirPermisosRolRequest) {
        rolService.definirPermisosParaRol(rolId, definirPermisosRolRequest.getPermisoIds());
        return ResponseEntity.ok("Permisos definidos para el rol exitosamente.");
    }

    @GetMapping("/roles/{rolId}/permisos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<RolResponse> obtenerPermisosDeRol(@PathVariable Long rolId) {
        Rol rol = rolService.obtenerRolPorNombre(rolService.obtenerRolPorId(rolId).getNombre());
        List<String> permisos = rolService.obtenerNombresDePermisosDeRol(rolId);
        return ResponseEntity.ok(new RolResponse(rol.getId(), rol.getNombre(), permisos));
    }

    @GetMapping("/roles/id/{rolId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable Long rolId) {
        Rol rol = rolService.obtenerRolPorId(rolId);
        return ResponseEntity.ok(rol);
    }
}