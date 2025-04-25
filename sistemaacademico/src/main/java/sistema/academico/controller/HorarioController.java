package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema.academico.DTO.HorarioGeneradoDTO;
import sistema.academico.DTO.HorarioRequestDTO;
import sistema.academico.entities.Horario;
import sistema.academico.services.HorarioService;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    // Endpoint para generar el horario de un estudiante
    @GetMapping("/generar/{matriculaId}")
    public ResponseEntity<List<HorarioGeneradoDTO>> generarHorario(@PathVariable Long matriculaId) {
        List<HorarioGeneradoDTO> horariosGenerados = horarioService.generarHorario(matriculaId);
        return new ResponseEntity<>(horariosGenerados, HttpStatus.OK);
    }

    // Endpoint para agregar un nuevo horario
    @PostMapping("/agregar")
    public ResponseEntity<HorarioGeneradoDTO> agregarHorario(@RequestBody HorarioRequestDTO horarioRequestDTO) {
        HorarioGeneradoDTO horarioGenerado = horarioService.agregarHorario(horarioRequestDTO);
        return new ResponseEntity<>(horarioGenerado, HttpStatus.CREATED);
    }

    // Endpoint para actualizar un horario existente
    @PutMapping("/actualizar/{horarioId}")
    public ResponseEntity<HorarioGeneradoDTO> actualizarHorario(@PathVariable Long horarioId, 
            @RequestBody HorarioRequestDTO horarioRequestDTO) {
        HorarioGeneradoDTO horarioActualizado = horarioService.actualizarHorario(horarioId, horarioRequestDTO);
        return new ResponseEntity<>(horarioActualizado, HttpStatus.OK);
    }

    // Endpoint para eliminar un horario
    @DeleteMapping("/eliminar/{horarioId}")
    public ResponseEntity<String> eliminarHorario(@PathVariable Long horarioId) {
        horarioService.eliminarHorario(horarioId);
        return new ResponseEntity<>("Horario eliminado exitosamente", HttpStatus.NO_CONTENT);
    }

    // Endpoint para listar horarios de un curso
    @GetMapping("/listar/{cursoId}")
    public ResponseEntity<List<HorarioGeneradoDTO>> listarHorariosPorCurso(@PathVariable Long cursoId) {
        List<HorarioGeneradoDTO> horarios = horarioService.listarHorariosPorCurso(cursoId);
        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    // Endpoint para obtener los detalles de un horario específico
    @GetMapping("/detalles/{horarioId}")
    public ResponseEntity<String> obtenerDetallesHorario(@PathVariable Long horarioId) {
        Horario horario = new Horario();
        // Asumiendo que obtienes el horario de alguna forma, por ejemplo desde la base de datos
        return new ResponseEntity<>(horarioService.obtenerDetalles(horario), HttpStatus.OK);
    }
}
