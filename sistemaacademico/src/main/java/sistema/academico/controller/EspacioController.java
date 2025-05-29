package sistema.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema.academico.DTO.*;
import sistema.academico.services.EspacioService;

@RestController
@RequestMapping("/api/espacios")
public class EspacioController {

    @Autowired
    private EspacioService espacioService;

    @PostMapping("/crear")
    public EspacioResponseDTO crearEspacio(@RequestBody EspacioRequestDTO dto) {
        return espacioService.crearEspacio(dto);
    }

    @GetMapping("/disponibles")
    public List<EspacioResponseDTO> obtenerEspaciosDisponibles() {
        return espacioService.obtenerEspaciosDisponibles();
    }

    @PutMapping("/actualizar/{id}")
    public EspacioResponseDTO actualizarEspacio(@PathVariable Long id, @RequestBody EspacioRequestDTO dto) {
        return espacioService.actualizarEspacio(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarEspacio(@PathVariable Long id) {
        return espacioService.eliminarEspacio(id);
    }
}
