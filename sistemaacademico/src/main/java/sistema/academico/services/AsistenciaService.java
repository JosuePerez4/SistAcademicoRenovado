package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.DTO.EstudianteAsistenciaResponseDTO;
import sistema.academico.DTO.ObtenerAsistenciasEntreFechasRequestDTO;
import sistema.academico.DTO.ObtenerAsistenciasPorInscripcionResponseDTO;
import sistema.academico.DTO.RegistrarAsistenciaRequestDTO;
import sistema.academico.DTO.RegistrarAsistenciaResponseDTO;
import sistema.academico.DTO.ReporteAsistenciaCursoResponseDTO;
import sistema.academico.entities.Asistencia;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Inscripcion;
import sistema.academico.enums.AsistenciaEstado;
import sistema.academico.repository.AsistenciaRepository;
import sistema.academico.repository.CursoRepository;
import sistema.academico.repository.InscripcionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private InscripcionRepository inscripcionRepository;

    // 1. Registrar una asistencia
    public RegistrarAsistenciaResponseDTO registrarAsistencia(RegistrarAsistenciaRequestDTO request) {
        Long inscripcionId = request.getInscripcionId();
        LocalDate fecha = request.getFecha();
        AsistenciaEstado estado = request.getEstado();
        String justificacion = request.getJustificacion();
        if (estado == AsistenciaEstado.JUSTIFICADO && justificacion == null) {
            throw new RuntimeException("Se requiere justificación para una asistencia justificada.");
        }

        // Obtener inscripción
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        // Verificar si existe asistencia
        if (existeAsistenciaEnFecha(inscripcionId, fecha)) {
            throw new RuntimeException("Ya existe una asistencia registrada para esta fecha.");
        }

        // Guardar asistencia
        Asistencia asistencia = new Asistencia();
        asistencia.setInscripcion(inscripcion);
        asistencia.setFecha(fecha);
        asistencia.setEstado(estado);
        asistencia.setJustificacion(justificacion);
        asistenciaRepository.save(asistencia);

        // Devolver respuesta
        RegistrarAsistenciaResponseDTO response = new RegistrarAsistenciaResponseDTO();
        response.setId(asistencia.getId());
        response.setInscripcionId(inscripcionId);
        response.setEstado(estado.name());
        response.setJustificacion(justificacion);
        return response;
    // 1. Registrar una asistencia
    public RegistrarAsistenciaResponseDTO registrarAsistencia(RegistrarAsistenciaRequestDTO request) {
        Long inscripcionId = request.getInscripcionId();
        LocalDate fecha = request.getFecha();
        AsistenciaEstado estado = request.getEstado();
        String justificacion = request.getJustificacion();
        if (estado == AsistenciaEstado.JUSTIFICADO && justificacion == null) {
            throw new RuntimeException("Se requiere justificación para una asistencia justificada.");
        }

        // Obtener inscripción
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        // Verificar si existe asistencia
        if (existeAsistenciaEnFecha(inscripcionId, fecha)) {
            throw new RuntimeException("Ya existe una asistencia registrada para esta fecha.");
        }

        // Guardar asistencia
        Asistencia asistencia = new Asistencia();
        asistencia.setInscripcion(inscripcion);
        asistencia.setFecha(fecha);
        asistencia.setEstado(estado);
        asistencia.setJustificacion(justificacion);
        asistenciaRepository.save(asistencia);

        // Devolver respuesta
        RegistrarAsistenciaResponseDTO response = new RegistrarAsistenciaResponseDTO();
        response.setId(asistencia.getId());
        response.setInscripcionId(inscripcionId);
        response.setEstado(estado.name());
        response.setJustificacion(justificacion);
        return response;
    }

    // 2. Obtener todas las asistencias por inscripción
    public List<ObtenerAsistenciasPorInscripcionResponseDTO> obtenerAsistenciasPorInscripcion(Long inscripcionId) {
        Inscripcion inscripcion = obtenerInscripcion(inscripcionId);
        List<Asistencia> asistencias = asistenciaRepository.findByInscripcion(inscripcion);

        List<ObtenerAsistenciasPorInscripcionResponseDTO> respuestas = new ArrayList<>();

        for (Asistencia asistencia : asistencias) {
            ObtenerAsistenciasPorInscripcionResponseDTO response = new ObtenerAsistenciasPorInscripcionResponseDTO();
            response.setFecha(asistencia.getFecha().toString());
            response.setEstado(asistencia.getEstado().name());
            response.setJustificacion(asistencia.getJustificacion());
            respuestas.add(response);
        }

        return respuestas;
    }

    // 3. Obtener asistencias entre fechas
    public List<ObtenerAsistenciasPorInscripcionResponseDTO> obtenerAsistenciasPorRango(
            ObtenerAsistenciasEntreFechasRequestDTO request) {
        Long inscripcionId = request.getInscripcionId();
        LocalDate inicio = request.getFechaInicio();
        LocalDate fin = request.getFechaFin();
        if (inicio.isAfter(fin)) {
            throw new RuntimeException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }
        if (inicio.isEqual(fin)) {
            throw new RuntimeException("La fecha de inicio no puede ser igual a la fecha de fin.");
        }
        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        Inscripcion inscripcion = obtenerInscripcion(inscripcionId);
        List<Asistencia> asistencias = asistenciaRepository.findByInscripcionAndFechaBetween(inscripcion, inicio, fin);

        List<ObtenerAsistenciasPorInscripcionResponseDTO> respuestas = new ArrayList<>();

        for (Asistencia asistencia : asistencias) {
            ObtenerAsistenciasPorInscripcionResponseDTO response = new ObtenerAsistenciasPorInscripcionResponseDTO();
            response.setFecha(asistencia.getFecha().toString());
            response.setEstado(asistencia.getEstado().name());
            response.setJustificacion(asistencia.getJustificacion());
            respuestas.add(response);
        }

        return respuestas;
    }

    // 4. Obtener solo asistencias justificadas
    public List<ObtenerAsistenciasPorInscripcionResponseDTO> obtenerAsistenciasJustificadas(Long inscripcionId) {
        Inscripcion inscripcion = obtenerInscripcion(inscripcionId);
        List<Asistencia> asistencias = asistenciaRepository.findByInscripcionAndEstado(inscripcion,
                AsistenciaEstado.JUSTIFICADO);

        List<ObtenerAsistenciasPorInscripcionResponseDTO> respuestas = new ArrayList<>();

        for (Asistencia asistencia : asistencias) {
            ObtenerAsistenciasPorInscripcionResponseDTO response = new ObtenerAsistenciasPorInscripcionResponseDTO();
            response.setFecha(asistencia.getFecha().toString());
            response.setEstado(asistencia.getEstado().name());
            response.setJustificacion(asistencia.getJustificacion());
            respuestas.add(response);
        }

        return respuestas;
    }

    // 5. Obtener solo ausencias injustificadas
    public List<ObtenerAsistenciasPorInscripcionResponseDTO> obtenerAusenciasInjustificadas(Long inscripcionId) {
        Inscripcion inscripcion = obtenerInscripcion(inscripcionId);
        List<Asistencia> asistencias = asistenciaRepository.findByInscripcionAndEstado(inscripcion,
                AsistenciaEstado.AUSENTE);

        List<ObtenerAsistenciasPorInscripcionResponseDTO> respuestas = new ArrayList<>();

        for (Asistencia asistencia : asistencias) {
            ObtenerAsistenciasPorInscripcionResponseDTO response = new ObtenerAsistenciasPorInscripcionResponseDTO();
            response.setFecha(asistencia.getFecha().toString());
            response.setEstado(asistencia.getEstado().name());
            response.setJustificacion(asistencia.getJustificacion());
            respuestas.add(response);
        }

        return respuestas;
    }

    // 6. Contar cuántas veces estuvo en cada estado de asistencia
    public Map<AsistenciaEstado, Long> contarPorEstado(Long inscripcionId) {
        Inscripcion inscripcion = obtenerInscripcion(inscripcionId);
        List<Asistencia> asistencias = asistenciaRepository.findByInscripcion(inscripcion);

        return asistencias.stream()
                .collect(Collectors.groupingBy(Asistencia::getEstado, Collectors.counting()));
    }

    // 7. Validar si ya existe asistencia para un día
    public boolean existeAsistenciaEnFecha(Long inscripcionId, LocalDate fecha) {
        Inscripcion inscripcion = obtenerInscripcion(inscripcionId);
        return asistenciaRepository.existsByInscripcionAndFecha(inscripcion, fecha);
    // 4. Obtener solo asistencias justificadas
    public List<ObtenerAsistenciasPorInscripcionResponseDTO> obtenerAsistenciasJustificadas(Long inscripcionId) {
        Inscripcion inscripcion = obtenerInscripcion(inscripcionId);
        List<Asistencia> asistencias = asistenciaRepository.findByInscripcionAndEstado(inscripcion,
                AsistenciaEstado.JUSTIFICADO);

        List<ObtenerAsistenciasPorInscripcionResponseDTO> respuestas = new ArrayList<>();

        for (Asistencia asistencia : asistencias) {
            ObtenerAsistenciasPorInscripcionResponseDTO response = new ObtenerAsistenciasPorInscripcionResponseDTO();
            response.setFecha(asistencia.getFecha().toString());
            response.setEstado(asistencia.getEstado().name());
            response.setJustificacion(asistencia.getJustificacion());
            respuestas.add(response);
        }

        return respuestas;
    }

    // 5. Obtener solo ausencias injustificadas
    public List<ObtenerAsistenciasPorInscripcionResponseDTO> obtenerAusenciasInjustificadas(Long inscripcionId) {
        Inscripcion inscripcion = obtenerInscripcion(inscripcionId);
        List<Asistencia> asistencias = asistenciaRepository.findByInscripcionAndEstado(inscripcion,
                AsistenciaEstado.AUSENTE);

        List<ObtenerAsistenciasPorInscripcionResponseDTO> respuestas = new ArrayList<>();

        for (Asistencia asistencia : asistencias) {
            ObtenerAsistenciasPorInscripcionResponseDTO response = new ObtenerAsistenciasPorInscripcionResponseDTO();
            response.setFecha(asistencia.getFecha().toString());
            response.setEstado(asistencia.getEstado().name());
            response.setJustificacion(asistencia.getJustificacion());
            respuestas.add(response);
        }

        return respuestas;
    }

    // 6. Contar cuántas veces estuvo en cada estado de asistencia
    public Map<AsistenciaEstado, Long> contarPorEstado(Long inscripcionId) {
        Inscripcion inscripcion = obtenerInscripcion(inscripcionId);
        List<Asistencia> asistencias = asistenciaRepository.findByInscripcion(inscripcion);

        return asistencias.stream()
                .collect(Collectors.groupingBy(Asistencia::getEstado, Collectors.counting()));
    }

    // 7. Validar si ya existe asistencia para un día
    public boolean existeAsistenciaEnFecha(Long inscripcionId, LocalDate fecha) {
        Inscripcion inscripcion = obtenerInscripcion(inscripcionId);
        return asistenciaRepository.existsByInscripcionAndFecha(inscripcion, fecha);
    }

    // 8. Determinar si aprueba o no en base a la cantidad de asistencias ausentes
    public boolean apruebaPorAsistencias(Long inscripcionId) {
        Inscripcion inscripcion = obtenerInscripcion(inscripcionId);
        List<Asistencia> asistencias = asistenciaRepository.findByInscripcion(inscripcion);

        int ausentes = (int) asistencias.stream()
                .filter(asistencia -> asistencia.getEstado() == AsistenciaEstado.AUSENTE)
                .count();

        int porcentajeAsistencias = (int) ((ausentes * 100.0) / asistencias.size());

        return porcentajeAsistencias >= 30;
    }

    // 9. Reporte general de asistencias por curso
    public ReporteAsistenciaCursoResponseDTO generarReporteAsistenciaPorCurso(Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + cursoId));

        List<Asistencia> asistenciasPresentes = asistenciaRepository
                .findByInscripcionCursoIdAndEstado(cursoId, AsistenciaEstado.PRESENTE);

        // Mapa para contar asistencias por estudiante
        Map<String, Long> conteoPorEstudiante = new HashMap<>();

        for (Asistencia asistencia : asistenciasPresentes) {
            String nombreEstudiante = asistencia.getInscripcion().getMatricula().getEstudiante().getNombre() + " "
                    + asistencia.getInscripcion().getMatricula().getEstudiante().getApellido();

            if (conteoPorEstudiante.containsKey(nombreEstudiante)) {
                conteoPorEstudiante.put(nombreEstudiante, conteoPorEstudiante.get(nombreEstudiante) + 1);
            } else {
                conteoPorEstudiante.put(nombreEstudiante, 1L);
            }
        }

        // Convertimos el mapa a una lista de DTOs
        List<EstudianteAsistenciaResponseDTO> listaAsistencias = new ArrayList<>();

        for (Map.Entry<String, Long> entry : conteoPorEstudiante.entrySet()) {
            EstudianteAsistenciaResponseDTO dto = new EstudianteAsistenciaResponseDTO(entry.getKey(), entry.getValue());
            listaAsistencias.add(dto);
        }

        return new ReporteAsistenciaCursoResponseDTO(curso.getNombre(), listaAsistencias);
    }

    // 10. Obtener detalles de todas las asistencias registradas en una fecha
    // específica
    public List<ObtenerAsistenciasPorInscripcionResponseDTO> obtenerAsistenciasPorFecha(LocalDate fecha) {
        List<Asistencia> asistencias = asistenciaRepository.findByFecha(fecha);
        List<ObtenerAsistenciasPorInscripcionResponseDTO> respuestas = new ArrayList<>();

        for (Asistencia asistencia : asistencias) {
            ObtenerAsistenciasPorInscripcionResponseDTO response = new ObtenerAsistenciasPorInscripcionResponseDTO();
            response.setFecha(asistencia.getFecha().toString());
            response.setEstado(asistencia.getEstado().name());
            response.setJustificacion(asistencia.getJustificacion());
            respuestas.add(response);
        }

        return respuestas;
    }

    // Utilidad privada
    private Inscripcion obtenerInscripcion(Long id) {
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
    }
}
