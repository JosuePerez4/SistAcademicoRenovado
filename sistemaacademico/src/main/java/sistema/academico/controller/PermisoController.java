package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistema.academico.DTO.PermisoResponse;
import sistema.academico.entities.Permiso;
import sistema.academico.services.PermisoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin") // También restringimos la gestión de permisos a administradores
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @PostMapping("/permisos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PermisoResponse> crearPermiso(@RequestBody String nombre) {
        Permiso nuevoPermiso = permisoService.crearPermiso(nombre);
        return new ResponseEntity<>(new PermisoResponse(nuevoPermiso.getId(), nuevoPermiso.getNombre()), HttpStatus.CREATED);
    }

    @GetMapping("/permisos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<PermisoResponse>> obtenerTodosLosPermisos() {
        List<Permiso> permisos = permisoService.obtenerTodosLosPermisos();
        List<PermisoResponse> permisoResponses = permisos.stream()
                .map(permiso -> new PermisoResponse(permiso.getId(), permiso.getNombre()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(permisoResponses);
    }

    @GetMapping("/permisos/id/{permisoId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Permiso> obtenerPermisoPorId(@PathVariable Long permisoId) {
        Permiso permiso = permisoService.obtenerPermisoPorId(permisoId);
        return ResponseEntity.ok(permiso);
    }
}