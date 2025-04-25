package sistema.academico.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.DTO.EstudianteAsistenciaResponseDTO;
import sistema.academico.DTO.ReporteAsistenciaCursoResponseDTO;
import sistema.academico.DTO.ReporteAsistenciasEstudianteResponseDTO;
import sistema.academico.DTO.ResumenAcademicoResponseDTO;
import sistema.academico.entities.Asistencia;
import sistema.academico.entities.Calificacion;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.Inscripcion;
import sistema.academico.enums.AsistenciaEstado;
import sistema.academico.repository.AsistenciaRepository;
import sistema.academico.repository.CalificacionRepository;
import sistema.academico.repository.CursoRepository;
import sistema.academico.repository.EstudianteRepository;
import sistema.academico.repository.InscripcionRepository;

@Service
public class ReporteService {

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private CalificacionRepository calificacionRepo;

    @Autowired
    private AsistenciaRepository asistenciaRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private InscripcionRepository inscripcionRepo;

    @Autowired
    private HistorialAcademicoService historialAcademicoService;

    public ReporteAsistenciasEstudianteResponseDTO generarReporteEstudiante(Long estudianteId) {
        Estudiante estudiante = estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        List<Asistencia> asistencias = asistenciaRepo.findByInscripcionMatriculaEstudiante(estudiante);

        long cantidadAsistencias = asistencias.size();
        long cantidadAsistenciasPresentes = 0;
        long cantidadFaltas = 0;
        long cantidadJustificadas = 0;
        double porcentajeAsistencia = (cantidadAsistenciasPresentes)/cantidadAsistencias * 100.0;

        for (Asistencia asistencia : asistencias) {
            if (asistencia.getEstado().equals(AsistenciaEstado.PRESENTE)) {
                cantidadAsistencias++;
            } else {
                if (asistencia.getEstado().equals(AsistenciaEstado.JUSTIFICADO)) {
                    cantidadJustificadas++;
                } else {
                    cantidadFaltas++;
                }
            }
        }

        return new ReporteAsistenciasEstudianteResponseDTO(
                estudiante.getNombre(),
                cantidadAsistencias,
                cantidadAsistenciasPresentes,
                cantidadFaltas,
                cantidadJustificadas,
                porcentajeAsistencia);
    }

    public Double obtenerPromedioCurso(Long cursoId) {
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        List<Inscripcion> inscripciones = inscripcionRepo.findByCurso(curso);

        double sumaNotas = 0;
        int totalNotas = 0;

        for (Inscripcion inscripcion : inscripciones) {
            List<Calificacion> calificaciones = inscripcion.getCalificaciones();

            for (Calificacion calificacion : calificaciones) {
                sumaNotas += calificacion.getNota();
                totalNotas++;
            }
        }

        if (totalNotas == 0) {
            return 0.0; // O puedes lanzar una excepción o retornar null si prefieres
        }

        return sumaNotas / totalNotas;
    }

    public ReporteAsistenciaCursoResponseDTO generarReporteAsistenciaCurso(Long cursoId) {
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        List<Inscripcion> inscripciones = inscripcionRepo.findByCurso(curso);
        List<EstudianteAsistenciaResponseDTO> listaAsistencias = new ArrayList<>();

        for (Inscripcion inscripcion : inscripciones) {
            List<Asistencia> asistencias = inscripcion.getAsistencias();

            String nombreEstudiante = inscripcion.getMatricula().getEstudiante().getNombre();
            long cantidadPresentes = 0;

            for (Asistencia asistencia : asistencias) {
                if (asistencia.getEstado().equals(AsistenciaEstado.PRESENTE)) {
                    cantidadPresentes++;
                }
            }

            EstudianteAsistenciaResponseDTO dto = new EstudianteAsistenciaResponseDTO(
                    nombreEstudiante,
                    cantidadPresentes);

            listaAsistencias.add(dto);
        }

        ReporteAsistenciaCursoResponseDTO response = new ReporteAsistenciaCursoResponseDTO();
        response.setNombreCurso(curso.getNombre());
        response.setAsistencias(listaAsistencias);

        return response;
    }

    public ResumenAcademicoResponseDTO generarResumenAcademico(Long estudianteId) {
        return historialAcademicoService.generarResumenAcademico(estudianteId);
    }
}
