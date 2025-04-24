package sistema.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import sistema.academico.DTO.CalificacionResponseDTO;
import sistema.academico.DTO.CursoResponseDTO;
import sistema.academico.DTO.HistorialAcademicoDTO;
import sistema.academico.DTO.HistorialAcademicoRequestDTO;
import sistema.academico.DTO.ListaCalificacionesResponseDTO;
=======
import sistema.academico.DTO.CursoResponseDTO;
import sistema.academico.DTO.HistorialAcademicoDTO;
import sistema.academico.DTO.HistorialAcademicoRequestDTO;
>>>>>>> 5e627dfaa2b239a4b261e69fc6fc036a23ca29f3
import sistema.academico.DTO.ResumenAcademicoResponseDTO;
import sistema.academico.entities.Curso;
import sistema.academico.services.HistorialAcademicoService;

@RestController
@RequestMapping("/api/historial-academico")
public class HistorialAcademicoController {

    @Autowired
    private HistorialAcademicoService historialService;

    @PostMapping("/crear")
    public ResponseEntity<HistorialAcademicoDTO> crearHistorial(@RequestBody HistorialAcademicoRequestDTO dto) {
        HistorialAcademicoDTO nuevoHistorialDTO = historialService.crearHistorialAcademico(dto);
        return ResponseEntity.ok(nuevoHistorialDTO);
    }

    @GetMapping("/estudiante/{estudianteId}")
    public List<HistorialAcademicoDTO> obtenerHistorialPorEstudiante(@PathVariable Long estudianteId) {
        return historialService.obtenerHistorialesPorEstudiante(estudianteId);
    }

<<<<<<< HEAD
    @GetMapping("/estudiante/{id}/obtener/calificaciones/historial")
    public List<ListaCalificacionesResponseDTO> obtenerCalificacionesPorEstudiante(
            @PathVariable("id") Long estudianteId) {
        return historialService.listarCalificacionesPorEstudiante(estudianteId);
    }

=======
>>>>>>> 5e627dfaa2b239a4b261e69fc6fc036a23ca29f3
    @GetMapping("/cursos-aprobados/{estudianteId}")
    public List<CursoResponseDTO> obtenerCursosAprobados(@PathVariable Long estudianteId) {
        return historialService.obtenerCursosAprobadosPorEstudiante(estudianteId);
    }

    @GetMapping("/cursos-reprobados/{estudianteId}")
    public List<CursoResponseDTO> obtenerCursosReprobados(@PathVariable Long estudianteId) {
        return historialService.obtenerCursosReprobadosPorEstudiante(estudianteId);
    }

    @GetMapping("/cursos-en-proceso/{estudianteId}")
    public List<CursoResponseDTO> obtenerCursosEnProceso(@PathVariable Long estudianteId) {
        return historialService.obtenerCursosEnProceso(estudianteId);
    }

    @PostMapping("/{historialId}/agregar-aprobado")
<<<<<<< HEAD
    public void agregarCursoAprobado(@PathVariable Long historialId, @RequestParam Long cursoId) {
        historialService.agregarCursoAprobado(historialId, cursoId);
    }

    @PostMapping("/{historialId}/agregar-reprobado")
    public void agregarCursoReprobado(@PathVariable Long historialId, @RequestParam Long cursoId) {
        historialService.agregarCursoReprobado(historialId, cursoId);
    }

    @PostMapping("/{historialId}/agregar-en-proceso")
    public void agregarCursoEnProceso(@PathVariable Long historialId, @RequestParam Long cursoId) {
        historialService.agregarCursoEnProceso(historialId, cursoId);
=======
    public void agregarCursoAprobado(@PathVariable Long historialId, @RequestBody Curso curso) {
        historialService.agregarCursoAprobado(historialId, curso);
    }

    @PostMapping("/{historialId}/agregar-reprobado")
    public void agregarCursoReprobado(@PathVariable Long historialId, @RequestBody Curso curso) {
        historialService.agregarCursoReprobado(historialId, curso);
    }

    @PostMapping("/{historialId}/agregar-en-proceso")
    public void agregarCursoEnProceso(@PathVariable Long historialId, @RequestBody Curso curso) {
        historialService.agregarCursoEnProceso(historialId, curso);
>>>>>>> 5e627dfaa2b239a4b261e69fc6fc036a23ca29f3
    }

    @PutMapping("/{historialId}/recalcular-desempeno")
    public HistorialAcademicoDTO recalcularDesempenoAcademico(@PathVariable Long historialId) {
        return historialService.recalcularDesempeñoAcademico(historialId);
    }

<<<<<<< HEAD
    @PutMapping("/actualizar/{estudianteId}")
    public ResponseEntity<HistorialAcademicoDTO> actualizarHistorialPorEstudiante(@PathVariable Long estudianteId) {
        try {
            HistorialAcademicoDTO historialAcademicoDTO = historialService
                    .actualizarHistorialPorEstudiante(estudianteId);
            return ResponseEntity.ok(historialAcademicoDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Si ocurre un error (por ejemplo, no se encuentra el
                                                           // historial)
        }
    }

=======
>>>>>>> 5e627dfaa2b239a4b261e69fc6fc036a23ca29f3
    @GetMapping("/resumen/{estudianteId}")
    public ResumenAcademicoResponseDTO generarResumen(@PathVariable Long estudianteId) {
        return historialService.generarResumenAcademico(estudianteId);
    }

    @GetMapping("/estado-academico/{estudianteId}")
    public String obtenerEstadoAcademico(@PathVariable Long estudianteId) {
        return historialService.obtenerEstadoAcademico(estudianteId);
    }
}
