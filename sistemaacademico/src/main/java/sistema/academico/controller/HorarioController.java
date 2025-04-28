package sistema.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sistema.academico.DTO.HorarioGeneradoDTO;
import sistema.academico.DTO.HorarioRequestDTO;
import sistema.academico.services.HorarioService;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping("/generar/{matriculaId}")
    public List<HorarioGeneradoDTO> generarHorario(@PathVariable Long matriculaId) {
        return horarioService.generarHorario(matriculaId);
    }

    @GetMapping("/{horarioId}")
    public HorarioGeneradoDTO obtenerDetalles(@PathVariable Long horarioId) {
        return horarioService.obtenerDetalles(horarioId);
    }

    @GetMapping("/curso/{cursoId}")
    public List<HorarioGeneradoDTO> listarHorariosPorCurso(@PathVariable Long cursoId) {
        return horarioService.listarHorariosPorCurso(cursoId);
    }

    @PostMapping
    public HorarioGeneradoDTO agregarHorario(@RequestBody HorarioRequestDTO horarioRequestDTO) {
        return horarioService.agregarHorario(horarioRequestDTO);
    }

    @PutMapping("/{horarioId}")
    public HorarioGeneradoDTO actualizarHorario(@PathVariable Long horarioId, @RequestBody HorarioRequestDTO horarioRequestDTO) {
        return horarioService.actualizarHorario(horarioId, horarioRequestDTO);
    }

    @DeleteMapping("/{horarioId}")
    public void eliminarHorario(@PathVariable Long horarioId) {
        horarioService.eliminarHorario(horarioId);
    }
}
