package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.academico.DTO.*;
import sistema.academico.services.EvaluacionService;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @PostMapping("/crear")
    public ResponseEntity<EvaluacionResponseDTO> crearEvaluacion(@RequestBody CrearEvaluacionDTO crearEvaluacionDTO) {
        EvaluacionResponseDTO evaluacionResponseDTO = evaluacionService.crearEvaluacionConHorario(crearEvaluacionDTO);
        return ResponseEntity.ok(evaluacionResponseDTO);
    }
}
