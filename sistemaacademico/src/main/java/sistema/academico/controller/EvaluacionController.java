package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sistema.academico.DTO.CrearEvaluacionDTO;
import sistema.academico.DTO.EvaluacionResponseDTO;
import sistema.academico.services.EvaluacionService;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @PostMapping
    public EvaluacionResponseDTO crearEvaluacion(@RequestBody CrearEvaluacionDTO crearEvaluacionDTO) {
        return evaluacionService.crearEvaluacionConHorario(crearEvaluacionDTO);
    }
}
