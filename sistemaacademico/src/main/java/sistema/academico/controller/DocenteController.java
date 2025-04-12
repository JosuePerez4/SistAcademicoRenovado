package sistema.academico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.academico.DTO.DocenteRequestDTO;
import sistema.academico.DTO.DocenteResponseDTO;
import sistema.academico.services.DocenteService;

import java.util.List;

@RestController
@RequestMapping("/docentes")
@RequiredArgsConstructor
public class DocenteController {

    private final DocenteService docenteService;

    @PostMapping("/{usuarioId}")
    public ResponseEntity<DocenteResponseDTO> crearDocente(
            @PathVariable Long usuarioId,
            @RequestBody DocenteRequestDTO dto) {
        return new ResponseEntity<>(docenteService.registrarDocente(usuarioId, dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocenteResponseDTO> actualizarDocente(
            @PathVariable Long id,
            @RequestBody DocenteRequestDTO dto) {
        return ResponseEntity.ok(docenteService.actualizarDocente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDocente(@PathVariable Long id) {
        docenteService.eliminarDocente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocenteResponseDTO> obtenerDocente(@PathVariable Long id) {
        return ResponseEntity.ok(docenteService.obtenerDocentePorId(id));
    }

    @GetMapping
    public ResponseEntity<List<DocenteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(docenteService.obtenerTodosLosDocentes());
    }
}
