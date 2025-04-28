package sistema.academico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sistema.academico.DTO.ComentarioRequestDTO;
import sistema.academico.DTO.ComentarioResponseDTO;
import sistema.academico.services.ComentarioEvaluacionService;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioEvaluacionController {

    private final ComentarioEvaluacionService comentarioService;

    @PostMapping
    public ComentarioResponseDTO agregarComentario(@RequestBody ComentarioRequestDTO dto) {
        return comentarioService.agregarComentario(dto);
    }

    @GetMapping("/evaluacion/{evaluacionId}")
    public List<ComentarioResponseDTO> listarComentarios(@PathVariable Long evaluacionId) {
        return comentarioService.listarComentarios(evaluacionId);
    }
}
