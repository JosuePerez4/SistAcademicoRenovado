/* package sistema.academico.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema.academico.DTO.ObtenerAsistenciasEntreFechasRequestDTO;
import sistema.academico.DTO.ObtenerAsistenciasPorInscripcionResponseDTO;
import sistema.academico.DTO.RegistrarAsistenciaRequestDTO;
import sistema.academico.DTO.RegistrarAsistenciaResponseDTO;
import sistema.academico.DTO.ReporteAsistenciaCursoResponseDTO;
import sistema.academico.enums.AsistenciaEstado;
import sistema.academico.services.AsistenciaService;

@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping("/registrar")
    public RegistrarAsistenciaResponseDTO registrarAsistencia(@RequestBody RegistrarAsistenciaRequestDTO request) {
        return asistenciaService.registrarAsistencia(request);
    }

    @GetMapping("/asistenciaPorInscripcion/{inscripcionId}")
    public List<ObtenerAsistenciasPorInscripcionResponseDTO> obtenerAsistenciasPorInscripcion(
            @PathVariable Long inscripcionId) {
        return asistenciaService.obtenerAsistenciasPorInscripcion(inscripcionId);
    }

    @GetMapping("/asistenciaPorInscripcion/{inscripcionId}/rangoFecha/{fecha}")
    public List<ObtenerAsistenciasPorInscripcionResponseDTO> obtenerAsistenciasPorRangoDeFecha(
            ObtenerAsistenciasEntreFechasRequestDTO request) {
        return asistenciaService.obtenerAsistenciasPorRango(request);
    }

    // 4. Obtener solo asistencias justificadas
    @GetMapping("/AsistenciasJustificadas/{inscripcionId}")
    public ResponseEntity<List<ObtenerAsistenciasPorInscripcionResponseDTO>> obtenerAsistenciasJustificadas(
            @PathVariable Long inscripcionId) {
        return ResponseEntity.ok(asistenciaService.obtenerAsistenciasJustificadas(inscripcionId));
    }

    // 5. Obtener solo ausencias injustificadas
    @GetMapping("/ausencias-injustificadas/{inscripcionId}")
    public ResponseEntity<List<ObtenerAsistenciasPorInscripcionResponseDTO>> obtenerAusenciasInjustificadas(
            @PathVariable Long inscripcionId) {
        return ResponseEntity.ok(asistenciaService.obtenerAusenciasInjustificadas(inscripcionId));
    }

    // 6. Contar cuántas veces estuvo en cada estado de asistencia
    @GetMapping("/contar-por-estado/{inscripcionId}")
    public ResponseEntity<Map<AsistenciaEstado, Long>> contarPorEstado(
            @PathVariable Long inscripcionId) {
        return ResponseEntity.ok(asistenciaService.contarPorEstado(inscripcionId));
    }

    // 7. Validar si ya existe asistencia para un día
    @GetMapping("/existe")
    public ResponseEntity<Boolean> existeAsistenciaEnFecha(
            @RequestParam Long inscripcionId,
            @RequestParam LocalDate fecha) {
        return ResponseEntity.ok(asistenciaService.existeAsistenciaEnFecha(inscripcionId, fecha));
    }

    // 8. Determinar si aprueba o no en base a la cantidad de asistencias ausentes
    @GetMapping("/aprueba/{inscripcionId}")
    public ResponseEntity<Boolean> apruebaPorAsistencias(@PathVariable Long inscripcionId) {
        return ResponseEntity.ok(asistenciaService.apruebaPorAsistencias(inscripcionId));
    }

    // 9. Reporte general de asistencias por curso
    @GetMapping("/reporte-por-curso/{cursoId}")
    public ResponseEntity<ReporteAsistenciaCursoResponseDTO> generarReporteAsistenciaPorCurso(
            @PathVariable Long cursoId) {
        ReporteAsistenciaCursoResponseDTO reporte = asistenciaService.generarReporteAsistenciaPorCurso(cursoId);
        return ResponseEntity.ok(reporte);
    }

    // 10. Obtener detalles de todas las asistencias registradas en una fecha
    // específica
    @GetMapping("/por-fecha")
    public ResponseEntity<List<ObtenerAsistenciasPorInscripcionResponseDTO>> obtenerAsistenciasPorFecha(
            @RequestParam LocalDate fecha) {
        return ResponseEntity.ok(asistenciaService.obtenerAsistenciasPorFecha(fecha));
    }

}
*/