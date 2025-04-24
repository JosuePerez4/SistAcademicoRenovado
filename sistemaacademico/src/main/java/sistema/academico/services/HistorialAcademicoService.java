package sistema.academico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.DTO.CursoResponseDTO;
import sistema.academico.DTO.HistorialAcademicoDTO;
import sistema.academico.DTO.HistorialAcademicoRequestDTO;
import sistema.academico.DTO.ResumenAcademicoResponseDTO;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.HistorialAcademico;
import sistema.academico.entities.Inscripcion;
import sistema.academico.entities.Matricula;
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
        public void agregarCursoAprobado(Long historialId, Curso curso) {
                HistorialAcademico historial = historialAcademicoRepository.findById(historialId).orElseThrow();
                historial.getCursosAprobados().add(curso);
                historialAcademicoRepository.save(historial);
        }

        // Agregar curso reprobado
        public void agregarCursoReprobado(Long historialId, Curso curso) {
                HistorialAcademico historial = historialAcademicoRepository.findById(historialId).orElseThrow();
                historial.getCursosReprobados().add(curso);
                historialAcademicoRepository.save(historial);
        }

        // Agregar curso en proceso
        public void agregarCursoEnProceso(Long historialId, Curso curso) {
                HistorialAcademico historial = historialAcademicoRepository.findById(historialId).orElseThrow();
                historial.getCursosEnProceso().add(curso);
                historialAcademicoRepository.save(historial);
        }

        // Recalcular el promedio y actualizar créditos acumulados
        public HistorialAcademicoDTO recalcularDesempeñoAcademico(Long historialId) {
                HistorialAcademico historial = historialAcademicoRepository.findById(historialId).orElseThrow();
                Estudiante estudiante = historial.getEstudiante();
                // Se deben obtener todas las inscripciones del estudiante mediante su matricula

                Matricula matricula = historialAcademicoRepository.findMatriculaByEstudianteId(estudiante.getId());

                List<Inscripcion> inscripciones = matricula.getInscripciones();
                // Se calcucla el promedio general en base a la nota final de cada curso
                // independientemente si aprobó o no, y en base a los créditos de cada curso
                double sumaNotas = 0;
                int creditosAcumulados = 0;
                for (Inscripcion inscripcion : inscripciones) {
                        if (inscripcion.getNotaFinal() != null) {
                                sumaNotas += inscripcion.getNotaFinal()
                                                * inscripcion.getCurso().getMateria().getCreditos();
                                creditosAcumulados += inscripcion.getCurso().getMateria().getCreditos();
                        }
                }
                float promedioGeneral = (float) sumaNotas / inscripciones.size();

                // Cursos aprobados
                List<Curso> cursosAprobados = null;
                for (Inscripcion inscripcion : inscripciones) {
                        if (inscripcion.getNotaFinal() != null && inscripcion.getNotaFinal() >= 3.0) {
                                cursosAprobados.add(inscripcion.getCurso());
                        }
                }
                // Cursos reprobados
                List<Curso> cursosReprobados = null;
                for (Inscripcion inscripcion : inscripciones) {
                        if (inscripcion.getNotaFinal() != null && inscripcion.getNotaFinal() < 3.0) {
                                cursosReprobados.add(inscripcion.getCurso());
                        }
                }
                // Cursos en proceso
                List<Curso> cursosEnProceso = null;
                for (Inscripcion inscripcion : inscripciones) {
                        if (inscripcion.getNotaFinal() == null) {
                                cursosEnProceso.add(inscripcion.getCurso());
                        }
                }
                // Actualizar el historial académico
                historial.setCursosAprobados(cursosAprobados);
                historial.setCursosReprobados(cursosReprobados);
                historial.setCursosEnProceso(cursosEnProceso);

                // Actualizar el promedio general y créditos acumulados

                historial.setPromedioGeneral(promedioGeneral);
                historial.setCreditosAcumulados(creditosAcumulados);
                return new HistorialAcademicoDTO(
                                historial.getId(),
                                historial.getEstudiante().getId(),
                                historial.getEstudiante().getNombre() + " " + historial.getEstudiante().getApellido(),
                                promedioGeneral,
                                creditosAcumulados,
                                historial.getEstadoAcademicoActual(),
                                historial.getTotalMateriasAprobadas(),
                                historial.getTotalMateriasReprobadas(),
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
