package sistema.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sistema.academico.DTO.CantidadCursosResponseDTO;
import sistema.academico.DTO.EnRiesgoResponseDTO;
import sistema.academico.DTO.PromedioResponseDTO;
import sistema.academico.services.EstadisticasAcademicasService;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasAcademicasController {

    @Autowired
    private EstadisticasAcademicasService estadisticasService;

    @GetMapping("/estudiante/{estudianteId}/promedio-general")
    public ResponseEntity<PromedioResponseDTO> calcularPromedioGeneral(@PathVariable Long estudianteId) {
        double promedio = estadisticasService.calcularPromedioGeneralEstudiante(estudianteId);
        PromedioResponseDTO response = new PromedioResponseDTO();
        response.setPromedio(promedio);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/estudiante/{estudianteId}/cursos-aprobados")
    public ResponseEntity<CantidadCursosResponseDTO> contarCursosAprobados(@PathVariable Long estudianteId) {
        long cantidad = estadisticasService.contarCursosAprobadosEstudiante(estudianteId);
        CantidadCursosResponseDTO response = new CantidadCursosResponseDTO();
        response.setCantidad(cantidad);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/estudiante/{estudianteId}/cursos-reprobados")
    public ResponseEntity<CantidadCursosResponseDTO> contarCursosReprobados(@PathVariable Long estudianteId) {
        long cantidad = estadisticasService.contarCursosReprobadosEstudiante(estudianteId);
        CantidadCursosResponseDTO response = new CantidadCursosResponseDTO();
        response.setCantidad(cantidad);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/estudiante/{estudianteId}/total-cursos-inscritos")
    public ResponseEntity<CantidadCursosResponseDTO> contarTotalCursosInscritos(@PathVariable Long estudianteId) {
        long cantidad = estadisticasService.contarTotalCursosInscritosEstudiante(estudianteId);
        CantidadCursosResponseDTO response = new CantidadCursosResponseDTO();
        response.setCantidad(cantidad);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/estudiante/{estudianteId}/promedio-proximo-semestre")
    public ResponseEntity<PromedioResponseDTO> proyectarPromedioProximoSemestre(@PathVariable Long estudianteId) {
        double promedio = estadisticasService.proyectarPromedioProximoSemestre(estudianteId);
        PromedioResponseDTO response = new PromedioResponseDTO();
        response.setPromedio(promedio);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/estudiante/{estudianteId}/en-riesgo")
    public ResponseEntity<EnRiesgoResponseDTO> estaEstudianteEnRiesgo(@PathVariable Long estudianteId) {
        boolean enRiesgo = estadisticasService.estaEstudianteEnRiesgo(estudianteId);
        EnRiesgoResponseDTO response = new EnRiesgoResponseDTO();
        response.setEnRiesgo(enRiesgo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/estudiante/{estudianteId}/semestre/{semestreId}/promedio")
    public ResponseEntity<PromedioResponseDTO> calcularPromedioPorSemestre(
            @PathVariable Long estudianteId, @PathVariable Long semestreId) {
        double promedio = estadisticasService.calcularPromedioPorSemestreEstudiante(estudianteId, semestreId);
        PromedioResponseDTO response = new PromedioResponseDTO();
        response.setPromedio(promedio);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}