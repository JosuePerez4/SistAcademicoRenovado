package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sistema.academico.DTO.*;
import sistema.academico.services.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    // Reporte de asistencia de un estudiante
    @GetMapping("/asistencia/estudiante/{id}")
    public ReporteAsistenciasEstudianteResponseDTO obtenerAsistenciaEstudiante(@PathVariable Long id) {
        return reporteService.generarReporteEstudiante(id);
    }

    // Reporte de asistencia por curso
    @GetMapping("/asistencia/curso/{id}")
    public ReporteAsistenciaCursoResponseDTO obtenerAsistenciaCurso(@PathVariable Long id) {
        return reporteService.generarReporteAsistenciaCurso(id);
    }

    // Promedio de calificaciones de un curso
    @GetMapping("/promedio/curso/{id}")
    public Double obtenerPromedioCurso(@PathVariable Long id) {
        return reporteService.obtenerPromedioCurso(id);
    }

    // Resumen académico de un estudiante
    @GetMapping("/resumen/estudiante/{id}")
    public ResumenAcademicoResponseDTO resumenAcademico(@PathVariable Long id) {
        return reporteService.generarResumenAcademico(id);
    }
}
