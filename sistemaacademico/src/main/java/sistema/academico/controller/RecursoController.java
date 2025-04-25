package sistema.academico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import sistema.academico.DTO.*;
import sistema.academico.services.RecursoService;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
@RequiredArgsConstructor
public class RecursoController {

    private final RecursoService recursoService;

    @PostMapping
    public ResponseEntity<RecursoDTO> crear(@RequestBody RecursoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recursoService.crearRecurso(dto));
    }

    @GetMapping
    public ResponseEntity<List<RecursoDTO>> listar() {
        return ResponseEntity.ok(recursoService.listarRecursos());
    }

    @PostMapping("/movimientos")
    public ResponseEntity<MovimientoRecursoDTO> movimiento(@RequestBody MovimientoRecursoDTO dto) {
        return ResponseEntity.ok(recursoService.registrarMovimiento(dto));
    }
}
