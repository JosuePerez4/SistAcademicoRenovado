package sistema.academico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.academico.DTO.CursoResponseDTO;
import sistema.academico.DTO.HistorialAcademicoDTO;
import sistema.academico.DTO.HistorialAcademicoRequestDTO;
import sistema.academico.DTO.ListaCalificacionesResponseDTO;
import sistema.academico.DTO.ResumenAcademicoResponseDTO;
import sistema.academico.DTO.calificacionesPorEstudianteResponseDTO;
import sistema.academico.entities.Calificacion;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.HistorialAcademico;
import sistema.academico.entities.Inscripcion;
import sistema.academico.entities.Matricula;
import sistema.academico.repository.CursoRepository;
import sistema.academico.repository.EstudianteRepository;
import sistema.academico.repository.HistorialAcademicoRepository;
import sistema.academico.repository.MatriculaRepository;

@Service
public class HistorialAcademicoService {
        @Autowired
        private HistorialAcademicoRepository historialAcademicoRepository;

        @Autowired
        private EstudianteRepository estudianteRepository;

        @Autowired
        private MatriculaRepository matriculaRepository;

        @Autowired
        private CursoRepository cursoRepository;

        public HistorialAcademicoDTO crearHistorialAcademico(HistorialAcademicoRequestDTO dto) {
                Optional<Estudiante> estudianteOpt = estudianteRepository.findById(dto.getEstudianteId());
                Optional<Matricula> matriculaOpt = matriculaRepository.findById(dto.getMatriculaId());

                if (estudianteOpt.isEmpty() || matriculaOpt.isEmpty()) {
                        throw new RuntimeException("Estudiante o Matrícula no encontrada");
                }

                HistorialAcademico historial = new HistorialAcademico();
                historial.setEstudiante(estudianteOpt.get());
                historial.setMatricula(matriculaOpt.get());

                HistorialAcademico guardado = historialAcademicoRepository.save(historial);

                Estudiante estudiante = estudianteOpt.get();

                // Aquí puedes ajustar los valores reales si tienes lógica para calcular
                // promedio, etc.
                return new HistorialAcademicoDTO(
                                guardado.getId(),
                                estudiante.getId(),
                                estudiante.getNombre() + " " + estudiante.getApellido(),
                                0.0f, // promedioGeneral
                                0, // creditosAcumulados
                                "En proceso", // estadoAcademicoActual
                                0, // totalMateriasAprobadas
                                0, // totalMateriasReprobadas
                                0, // totalMateriasEnProceso
                                new ArrayList<>(), // cursosAprobados
                                new ArrayList<>(), // cursosReprobados
                                new ArrayList<>() // cursosEnProceso
                );
        }

        // Obtener historial académico por estudiante
        public List<HistorialAcademicoDTO> obtenerHistorialesPorEstudiante(Long estudianteId) {

                List<HistorialAcademico> historiales = historialAcademicoRepository.findByEstudianteId(estudianteId);

                return historiales.stream().map(historial -> {
                        // Mapear cursos aprobados
                        List<CursoResponseDTO> cursosAprobados = historial.getCursosAprobados().stream()
                                        .map(curso -> new CursoResponseDTO(
                                                        curso.getId(),
                                                        curso.getNombre(),
                                                        curso.getCodigo(),
                                                        curso.getMateria().getCreditos()))
                                        .toList();

                        // Mapear cursos reprobados
                        List<CursoResponseDTO> cursosReprobados = historial.getCursosReprobados().stream()
                                        .map(curso -> new CursoResponseDTO(
                                                        curso.getId(),
                                                        curso.getNombre(),
                                                        curso.getCodigo(),
                                                        curso.getMateria().getCreditos()))
                                        .toList();

                        // Mapear cursos en proceso
                        List<CursoResponseDTO> cursosEnProceso = historial.getCursosEnProceso().stream()
                                        .map(curso -> new CursoResponseDTO(
                                                        curso.getId(),
                                                        curso.getNombre(),
                                                        curso.getCodigo(),
                                                        curso.getMateria().getCreditos()))
                                        .toList();
                        // Retornar el DTO armado
                        return new HistorialAcademicoDTO(
                                        historial.getId(),
                                        historial.getEstudiante().getId(),
                                        historial.getEstudiante().getNombre() + " "
                                                        + historial.getEstudiante().getApellido(),
                                        historial.getPromedioGeneral(),
                                        historial.getCreditosAcumulados(),
                                        historial.getEstadoAcademicoActual(),
                                        historial.getTotalMateriasAprobadas(),
                                        historial.getTotalMateriasReprobadas(),
                                        historial.getTotalMateriasEnProceso(),
                                        cursosAprobados,
                                        cursosReprobados,
                                        cursosEnProceso);
                }).toList();
        }

        // Obtener todos los cursos aprobados por estudiante
        public List<CursoResponseDTO> obtenerCursosAprobadosPorEstudiante(Long estudianteId) {

                List<HistorialAcademico> historiales = historialAcademicoRepository.findByEstudianteId(estudianteId);
                return historiales.stream().flatMap(historial -> historial.getCursosAprobados().stream())
                                .map(curso -> new CursoResponseDTO(
                                                curso.getId(),
                                                curso.getNombre(),
                                                curso.getCodigo(),
                                                curso.getMateria().getCreditos()))
                                .toList();
        }

        // Obtener todos los cursos reprobados por estudiante
        public List<CursoResponseDTO> obtenerCursosReprobadosPorEstudiante(Long estudianteId) {
                List<HistorialAcademico> historiales = historialAcademicoRepository.findByEstudianteId(estudianteId);
                return historiales.stream().flatMap(historial -> historial.getCursosReprobados().stream())
                                .map(curso -> new CursoResponseDTO(
                                                curso.getId(),
                                                curso.getNombre(),
                                                curso.getCodigo(),
                                                curso.getMateria().getCreditos()))
                                .toList();
        }

        // Obtener todos los cursos en proceso por estudiante (no está aprobado ni
        // reprobado)
        public List<CursoResponseDTO> obtenerCursosEnProceso(Long estudianteId) {
                List<HistorialAcademico> historiales = historialAcademicoRepository.findByEstudianteId(estudianteId);
                return historiales.stream().flatMap(historial -> historial.getCursosEnProceso().stream())
                                .map(curso -> new CursoResponseDTO(
                                                curso.getId(),
                                                curso.getNombre(),
                                                curso.getCodigo(),
                                                curso.getMateria().getCreditos()))
                                .toList();
        }

        // Agregar curso aprobado
        public void agregarCursoAprobado(Long historialId, Long cursoId) {
                HistorialAcademico historial = historialAcademicoRepository.findById(historialId).orElseThrow();

                Curso curso = cursoRepository.findById(cursoId)
                                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
                // Verificar si el curso ya está en la lista de cursos aprobados
                if (historial.getCursosAprobados().stream().anyMatch(c -> c.getId().equals(cursoId))) {
                        throw new RuntimeException("El curso ya está en la lista de cursos aprobados");
                }
        }

        // Agregar curso reprobado
        public void agregarCursoReprobado(Long historialId, Long cursoId) {
                HistorialAcademico historial = historialAcademicoRepository.findById(historialId).orElseThrow();
                Curso curso = cursoRepository.findById(cursoId)
                                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
                // Verificar si el curso ya está en la lista de cursos reprobados
                if (historial.getCursosReprobados().stream().anyMatch(c -> c.getId().equals(cursoId))) {
                        throw new RuntimeException("El curso ya está en la lista de cursos reprobados");
                }
        }

        // Agregar curso en proceso
        public void agregarCursoEnProceso(Long historialId, Long cursoId) {
                HistorialAcademico historial = historialAcademicoRepository.findById(historialId).orElseThrow();
                Curso curso = cursoRepository.findById(cursoId)
                                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
                // Verificar si el curso ya está en la lista de cursos en proceso
                if (historial.getCursosEnProceso().stream().anyMatch(c -> c.getId().equals(cursoId))) {
                        throw new RuntimeException("El curso ya está en la lista de cursos en proceso");
                }
        }

        public List<ListaCalificacionesResponseDTO> listarCalificacionesPorEstudiante(Long estudianteId) {
                List<HistorialAcademico> historiales = historialAcademicoRepository.findByEstudianteId(estudianteId);
                List<ListaCalificacionesResponseDTO> resultado = new ArrayList<>();

                for (HistorialAcademico historial : historiales) {
                        Estudiante estudiante = historial.getEstudiante();
                        String nombreEstudiante = estudiante.getNombre() + " " + estudiante.getApellido();
                        String codigoEstudiante = estudiante.getCodigo(); // Asegúrate de tener este campo

                        List<Inscripcion> inscripciones = historial.getMatricula().getInscripciones();

                        for (Inscripcion inscripcion : inscripciones) {
                                String nombreCurso = inscripcion.getCurso().getNombre();
                                String codigoCurso = inscripcion.getCurso().getCodigo();

                                List<calificacionesPorEstudianteResponseDTO> calificacionesList = new ArrayList<>();

                                for (Calificacion calificacion : inscripcion.getCalificaciones()) {
                                        calificacionesList.add(new calificacionesPorEstudianteResponseDTO(
                                                        calificacion.getNota(),
                                                        calificacion.getEvaluacion().getTipo().name(),
                                                        calificacion.getEvaluacion().getDescripcion(),
                                                        calificacion.getEvaluacion().getFechaCreacion()));
                                }

                                ListaCalificacionesResponseDTO dto = new ListaCalificacionesResponseDTO(
                                                nombreEstudiante,
                                                codigoEstudiante,
                                                nombreCurso,
                                                codigoCurso,
                                                calificacionesList);

                                resultado.add(dto);
                        }
                }

                return resultado;
        }

        public HistorialAcademicoDTO recalcularDesempeñoAcademico(Long historialId) {
                HistorialAcademico historial = historialAcademicoRepository.findById(historialId)
                                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));

                Estudiante estudiante = historial.getEstudiante();

                // Obtener todas las matrículas ordenadas (más recientes primero)
                List<Matricula> matriculas = matriculaRepository
                                .findByEstudianteIdOrderByFechaMatriculaDesc(estudiante.getId());

                if (matriculas.isEmpty()) {
                        throw new RuntimeException("El estudiante no tiene matrículas registradas.");
                }

                List<Inscripcion> todasLasInscripciones = new ArrayList<>();
                for (Matricula matricula : matriculas) {
                        todasLasInscripciones.addAll(matricula.getInscripciones());
                }

                double sumaNotas = 0;
                int creditosAcumulados = 0;

                List<Curso> cursosAprobados = new ArrayList<>();
                List<Curso> cursosReprobados = new ArrayList<>();
                List<Curso> cursosEnProceso = new ArrayList<>();

                for (Inscripcion inscripcion : todasLasInscripciones) {
                        Curso curso = inscripcion.getCurso();
                        Integer creditos = curso.getMateria().getCreditos();

                        if (inscripcion.getNotaFinal() == null) {
                                cursosEnProceso.add(curso);
                        } else {
                                sumaNotas += inscripcion.getNotaFinal();

                                if (inscripcion.getNotaFinal() >= 3.0) {
                                        cursosAprobados.add(curso);
                                        creditosAcumulados += creditos;
                                } else {
                                        cursosReprobados.add(curso);
                                }
                        }
                }

                float promedioGeneral = todasLasInscripciones.isEmpty()
                                ? 0
                                : (float) sumaNotas / todasLasInscripciones.size();

                // Actualizar historial académico automáticamente
                historial.setCursosAprobados(cursosAprobados);
                historial.setCursosReprobados(cursosReprobados);
                historial.setCursosEnProceso(cursosEnProceso);
                historial.setPromedioGeneral(promedioGeneral);
                historial.setCreditosAcumulados(creditosAcumulados);
                historial.setTotalMateriasAprobadas(cursosAprobados.size());
                historial.setTotalMateriasReprobadas(cursosReprobados.size());
                historial.setTotalMateriasEnProceso(cursosEnProceso.size());

                historialAcademicoRepository.save(historial); // Guardar los cambios

                // Retornar DTO actualizado
                return new HistorialAcademicoDTO(
                                historial.getId(),
                                estudiante.getId(),
                                estudiante.getNombre() + " " + estudiante.getApellido(),
                                promedioGeneral,
                                creditosAcumulados,
                                historial.getEstadoAcademicoActual(),
                                cursosAprobados.size(),
                                cursosReprobados.size(),
                                cursosEnProceso.size(),
                                cursosAprobados.stream().map(curso -> new CursoResponseDTO(
                                                curso.getId(),
                                                curso.getNombre(),
                                                curso.getCodigo(),
                                                curso.getMateria().getCreditos())).toList(),
                                cursosReprobados.stream().map(curso -> new CursoResponseDTO(
                                                curso.getId(),
                                                curso.getNombre(),
                                                curso.getCodigo(),
                                                curso.getMateria().getCreditos())).toList(),
                                cursosEnProceso.stream().map(curso -> new CursoResponseDTO(
                                                curso.getId(),
                                                curso.getNombre(),
                                                curso.getCodigo(),
                                                curso.getMateria().getCreditos())).toList());
        }

        // Actualizar historial académico por estudiante
        public HistorialAcademicoDTO actualizarHistorialPorEstudiante(Long estudianteId) {
                List<HistorialAcademico> historiales = historialAcademicoRepository.findByEstudianteId(estudianteId);
                if (historiales.isEmpty())
                        throw new RuntimeException("No se encontró historial para el estudiante");

                return recalcularDesempeñoAcademico(historiales.get(0).getId());
        }

        // Generar un resumen general de desempeño por estudiante
        public ResumenAcademicoResponseDTO generarResumenAcademico(Long estudianteId) {
                // Obtener el historial académico del estudiante
                HistorialAcademico historial = historialAcademicoRepository.findByEstudianteId(estudianteId).get(0);
                // Transformar el historial académico a un DTO
                ResumenAcademicoResponseDTO resumen = new ResumenAcademicoResponseDTO(
                                historial.getEstudiante().getNombre() + " " + historial.getEstudiante().getApellido(),
                                historial.getPromedioGeneral(),
                                historial.getCreditosAcumulados(),
                                historial.getTotalMateriasAprobadas(),
                                historial.getTotalMateriasReprobadas(),
                                historial.getTotalMateriasEnProceso(),
                                historial.getEstadoAcademicoActual());
                return resumen;
        }

        // Obtener estado académico actual por estudiante
        public String obtenerEstadoAcademico(Long estudianteId) {
                HistorialAcademico historial = historialAcademicoRepository.findByEstudianteId(estudianteId).get(0);
                return historial.getEstadoAcademicoActual();
        }
}