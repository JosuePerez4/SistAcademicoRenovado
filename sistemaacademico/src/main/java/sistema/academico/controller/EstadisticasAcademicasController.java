package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sistema.academico.services.EstadisticasAcademicasService;

@RestController
@RequestMapping("/api/estadisticas-academicas")
public class EstadisticasAcademicasController {

    @Autowired
    private EstadisticasAcademicasService estadisticasAcademicasService;

    @GetMapping("/estudiante/{estudianteId}/promedio-general")
    public ResponseEntity<Double> calcularPromedioGeneralEstudiante(@PathVariable Long estudianteId) {
        double promedio = estadisticasAcademicasService.calcularPromedioGeneralEstudiante(estudianteId);
        return ResponseEntity.ok(promedio);
    }

    @GetMapping("/estudiante/{estudianteId}/cursos-aprobados")
    public ResponseEntity<Long> contarCursosAprobadosEstudiante(@PathVariable Long estudianteId) {
        long aprobados = estadisticasAcademicasService.contarCursosAprobadosEstudiante(estudianteId);
        return ResponseEntity.ok(aprobados);
    }

    @GetMapping("/estudiante/{estudianteId}/cursos-reprobados")
    public ResponseEntity<Long> contarCursosReprobadosEstudiante(@PathVariable Long estudianteId) {
        long reprobados = estadisticasAcademicasService.contarCursosReprobadosEstudiante(estudianteId);
        return ResponseEntity.ok(reprobados);
    }

    @GetMapping("/estudiante/{estudianteId}/total-cursos-inscritos")
    public ResponseEntity<Long> contarTotalCursosInscritosEstudiante(@PathVariable Long estudianteId) {
        long totalInscritos = estadisticasAcademicasService.contarTotalCursosInscritosEstudiante(estudianteId);
        return ResponseEntity.ok(totalInscritos);
    }

    @GetMapping("/estudiante/{estudianteId}/promedio-proyectado")
    public ResponseEntity<Double> proyectarPromedioProximoSemestre(@PathVariable Long estudianteId) {
        double promedioProyectado = estadisticasAcademicasService.proyectarPromedioProximoSemestre(estudianteId);
        return ResponseEntity.ok(promedioProyectado);
    }

    @GetMapping("/estudiante/{estudianteId}/en-riesgo")
    public ResponseEntity<Boolean> estaEstudianteEnRiesgo(@PathVariable Long estudianteId) {
        boolean enRiesgo = estadisticasAcademicasService.estaEstudianteEnRiesgo(estudianteId);
        return ResponseEntity.ok(enRiesgo);
    }
}