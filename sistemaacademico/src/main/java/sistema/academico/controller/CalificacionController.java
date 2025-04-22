package sistema.academico.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema.academico.DTO.CalificacionCursoResponseDTO;
import sistema.academico.DTO.CalificacionEstudianteResponseDTO;
import sistema.academico.DTO.CalificacionRequestDTO;
import sistema.academico.DTO.CalificacionResponseDTO;
import sistema.academico.entities.Calificacion;
import sistema.academico.entities.Estudiante;
import sistema.academico.services.RegistroCalificacionService;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    @Autowired
    private RegistroCalificacionService registroCalificacionService;

    @PostMapping
    public ResponseEntity<String> crearCalificacion(@RequestBody CalificacionRequestDTO requestDTO) {
        boolean registrada = registroCalificacionService.registrarCalificacion(
                requestDTO.getInscripcionId(),
                requestDTO.getEvaluacionId(),
                requestDTO.getNota()
        );
        if (registrada) {
            return new ResponseEntity<>("Calificación registrada exitosamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("No se pudo registrar la calificación. Verifica la inscripción y la evaluación.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarCalificacion(@PathVariable Long id, @RequestBody CalificacionRequestDTO requestDTO) {
        boolean actualizada = registroCalificacionService.modificarCalificacion(id, requestDTO.getNota());
        if (actualizada) {
            return new ResponseEntity<>("Calificación actualizada exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró la calificación con el ID proporcionado.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<CalificacionEstudianteResponseDTO>> obtenerCalificacionesPorEstudiante(@PathVariable Long estudianteId) {
        List<Calificacion> calificaciones = registroCalificacionService.obtenerCalificacionesPorEstudiante(estudianteId);
        if (calificaciones != null && !calificaciones.isEmpty()) {
            List<CalificacionEstudianteResponseDTO> responseDTOs = calificaciones.stream()
                    .map(this::convertToCalificacionEstudianteResponseDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<CalificacionCursoResponseDTO>> obtenerCalificacionesPorCurso(@PathVariable Long cursoId) {
        List<Calificacion> calificaciones = registroCalificacionService.obtenerCalificacionesPorCurso(cursoId);
        if (calificaciones != null && !calificaciones.isEmpty()) {
            List<CalificacionCursoResponseDTO> responseDTOs = calificaciones.stream()
                    .map(this::convertToCalificacionCursoResponseDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private CalificacionResponseDTO convertToResponseDTO(Calificacion calificacion) {
        CalificacionResponseDTO responseDTO = new CalificacionResponseDTO();
        responseDTO.setId(calificacion.getId());
        responseDTO.setNota(calificacion.getNota());
        responseDTO.setInscripcion(calificacion.getInscripcion());
        responseDTO.setEvaluacion(calificacion.getEvaluacion());
        return responseDTO;
    }

    private CalificacionEstudianteResponseDTO convertToCalificacionEstudianteResponseDTO(Calificacion calificacion) {
        CalificacionEstudianteResponseDTO responseDTO = new CalificacionEstudianteResponseDTO();
        responseDTO.setIdCalificacion(calificacion.getId());
        responseDTO.setNota(calificacion.getNota());
        responseDTO.setIdCurso(calificacion.getInscripcion().getCurso().getId());
        responseDTO.setNombreCurso(calificacion.getInscripcion().getCurso().getNombre());
        responseDTO.setTipoEvaluacion(calificacion.getEvaluacion().getTipo().toString());
        return responseDTO;
    }

    private CalificacionCursoResponseDTO convertToCalificacionCursoResponseDTO(Calificacion calificacion) {
        CalificacionCursoResponseDTO responseDTO = new CalificacionCursoResponseDTO();
        responseDTO.setIdCalificacion(calificacion.getId());
        responseDTO.setNota(calificacion.getNota());
        Estudiante estudiante = calificacion.getInscripcion().getMatricula().getEstudiante();
        responseDTO.setIdEstudiante(estudiante.getId());
        responseDTO.setNombreCompletoEstudiante(estudiante.getNombre() + " " + estudiante.getApellido());
        responseDTO.setTipoEvaluacion(calificacion.getEvaluacion().getTipo().toString());
        return responseDTO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCalificacion(@PathVariable Long id) {
        boolean eliminada = registroCalificacionService.eliminarCalificacion(id);
        if (eliminada) {
            return new ResponseEntity<>("Calificación eliminada exitosamente", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("No se encontró la calificación con el ID proporcionado.", HttpStatus.NOT_FOUND);
        }
    }
}