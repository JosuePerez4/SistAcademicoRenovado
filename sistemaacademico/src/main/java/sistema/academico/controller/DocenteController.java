package sistema.academico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.academico.DTO.DocenteRegistroRequestDTO;
import sistema.academico.DTO.DocenteResponseDTO;
import sistema.academico.DTO.DocenteUpdateDTO;
import sistema.academico.services.DocenteService;

import java.util.List;

@RestController
@RequestMapping("/docentes")
@RequiredArgsConstructor
public class DocenteController {

    private final DocenteService docenteService;

    // ✅ Crear docente (datos personales + profesionales)
    @PostMapping
    public ResponseEntity<DocenteResponseDTO> crearDocente(@RequestBody DocenteRegistroRequestDTO dto) {
        DocenteResponseDTO creado = docenteService.registrarDocenteConUsuario(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    // ✅ Actualizar solo datos profesionales del docente
    @PutMapping("/ActualizarDatosProfesionales/{id}")
    public ResponseEntity<DocenteResponseDTO> actualizarDocente(
            @PathVariable Long id,
            @RequestBody DocenteUpdateDTO dto) {
        return ResponseEntity.ok(docenteService.actualizarDocente(id, dto));
    }
    
    // ✅ Eliminar docente
    @DeleteMapping("/EliminarDocente/{id}")
    public ResponseEntity<Void> eliminarDocente(@PathVariable Long id) {
        docenteService.eliminarDocente(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Obtener un docente por ID
    @GetMapping("/ObtenerDocente/{id}")
    public ResponseEntity<DocenteResponseDTO> obtenerDocente(@PathVariable Long id) {
        return ResponseEntity.ok(docenteService.obtenerDocentePorId(id));
    }

    // ✅ Obtener todos los docentes
    @GetMapping("/ObtenerListaDeDocentes/{id}")
    public ResponseEntity<List<DocenteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(docenteService.obtenerTodosLosDocentes());
    }
}
