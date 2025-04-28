package sistema.academico.controller;

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

    @PostMapping
    public ResponseEntity<EspacioResponseDTO> crearEspacio(@RequestBody EspacioRequestDTO espacioRequestDTO) {
        EspacioResponseDTO espacioResponseDTO = espacioService.crearEspacio(espacioRequestDTO);
        return ResponseEntity.ok(espacioResponseDTO);
    }
}
