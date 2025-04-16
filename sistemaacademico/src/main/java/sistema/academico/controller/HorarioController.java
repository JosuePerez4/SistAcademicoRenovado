package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.academico.entities.Horario;
import sistema.academico.services.HorarioService;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    // Generar un horario (puedes implementar la lógica en el servicio)
    @PostMapping("/generar")
    public ResponseEntity<String> generarHorario() {
        String resultado = horarioService.generarHorario();
        return ResponseEntity.ok(resultado != null ? resultado : "Horario generado correctamente.");
    }

    // Validar si hay solapamiento con otro horario
    @PostMapping("/validarSolapamiento")
    public ResponseEntity<Boolean> validarSolapamiento(@RequestBody Horario nuevoHorario) {
        boolean haySolapamiento = horarioService.validarSolapamiento(nuevoHorario);
        return ResponseEntity.ok(haySolapamiento);
    }

    // Actualizar un horario existente
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarHorario(
            @RequestParam String diaSemana,
            @RequestParam Time horaInicio,
            @RequestParam String aula,
            @RequestParam String nuevoDia,
            @RequestParam Time nuevaHoraInicio,
            @RequestParam Time nuevaHoraFin,
            @RequestParam String nuevaAula) {
        horarioService.actualizarHorario(diaSemana, horaInicio, aula, nuevoDia, nuevaHoraInicio, nuevaHoraFin, nuevaAula);
        return ResponseEntity.ok("Horario actualizado correctamente.");
    }

    // Obtener detalles de un horario
    @GetMapping("/detalles")
    public ResponseEntity<String> obtenerDetalles(@RequestBody Horario horario) {
        String detalles = horarioService.obtenerDetalles(horario);
        return ResponseEntity.ok(detalles != null ? detalles : "No se encontraron detalles para el horario.");
    }

    // Listar horarios por curso
    @GetMapping("/listarPorCurso/{cursoId}")
    public ResponseEntity<List<Horario>> listarHorariosPorCurso(@PathVariable Long cursoId) {
        List<Horario> horarios = horarioService.listarHorariosPorCurso(cursoId);
        return ResponseEntity.ok(horarios);
    }
}