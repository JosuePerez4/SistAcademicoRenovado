package sistema.academico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.academico.DTO.AsignacionCursoDTO;
import sistema.academico.DTO.AsignacionCursoResponseDTO;
import sistema.academico.services.AsignacionService;

import java.util.List;

@RestController
@RequestMapping("/api/asignacion")
@RequiredArgsConstructor
public class AsignacionController {

    private final AsignacionService asignacionService;

    @PostMapping
    public ResponseEntity<AsignacionCursoResponseDTO> asignarCurso(@RequestBody AsignacionCursoDTO dto) {
        return new ResponseEntity<>(asignacionService.asignarCurso(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AsignacionCursoResponseDTO>> listarAsignaciones() {
        return ResponseEntity.ok(asignacionService.listarAsignaciones());
    }
}
