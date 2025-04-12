package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.academico.services.AsignacionService;

@RestController
@RequestMapping("/asignacion")
public class AsignacionController {

    private final AsignacionService asignacionService;

    @Autowired
    public AsignacionController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @PostMapping("/automatica")
    public ResponseEntity<String> asignar() {
        asignacionService.asignarCursosAutomaticamente();
        return ResponseEntity.ok("Asignación automática completada");
    }
}
