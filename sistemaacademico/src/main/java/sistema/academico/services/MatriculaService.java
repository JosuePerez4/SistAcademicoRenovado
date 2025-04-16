package sistema.academico.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistema.academico.DTO.ActualizarEstadoMatriculaRequestDTO;
import sistema.academico.DTO.ActualizarEstadoMatriculaResponseDTO;
import sistema.academico.DTO.EstudianteMatriculaDTO;
import sistema.academico.DTO.MatriculaEstadoDTO;
import sistema.academico.DTO.MatriculaEstudianteExistenteRequestDTO;
import sistema.academico.DTO.MatriculaEstudianteExistenteResponsetDTO;
import sistema.academico.DTO.MatriculaPrimeraVezRequestDTO;
import sistema.academico.DTO.MatriculaPrimeraVezResponseDTO;
import sistema.academico.DTO.MatriculaResponseDTO;
import sistema.academico.DTO.MatriculaResumenEstudianteDTO;
import sistema.academico.DTO.ProgramaAcademicoMatriculaDTO;
import sistema.academico.DTO.SemestreMatriculaDTO;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.Inscripcion;
import sistema.academico.entities.Matricula;
import sistema.academico.entities.ProgramaAcademico;
import sistema.academico.entities.Semestre;
import sistema.academico.enums.EstadoInscripcion;
import sistema.academico.enums.EstadoMatricula;
import sistema.academico.repository.EstudianteRepository;
import sistema.academico.repository.InscripcionRepository;
import sistema.academico.repository.MatriculaRepository;
import sistema.academico.repository.ProgramaAcademicoRepository;
import sistema.academico.repository.SemestreRepository;

@Service
public class MatriculaService {
    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private ProgramaAcademicoRepository programaAcademicoRepository;

    public MatriculaResponseDTO obtenerMatriculaPorId(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Matrícula no encontrada"));

        MatriculaResponseDTO response = convertirAMatriculaResponseDTO(matricula);
        return response;
    }

    public List<MatriculaResponseDTO> obtenerTodas() {
        List<Matricula> matriculas = matriculaRepository.findAll();
        List<MatriculaResponseDTO> responseList = new ArrayList<>();

        for (Matricula matricula : matriculas) {
            MatriculaResponseDTO dto = convertirAMatriculaResponseDTO(matricula);
            responseList.add(dto);
        }

        return responseList;
    }

    public List<MatriculaResumenEstudianteDTO> obtenerPorEstudiante(Long estudianteId) {
        List<Matricula> matriculas = matriculaRepository.findByEstudianteId(estudianteId);
        List<MatriculaResumenEstudianteDTO> responseList = new ArrayList<>();

        for (Matricula matricula : matriculas) {
            MatriculaResumenEstudianteDTO dto = new MatriculaResumenEstudianteDTO();
            dto.setId(matricula.getId());
            dto.setNombreEstudiante(matricula.getEstudiante().getNombre());
            dto.setCodigoEstudiante(matricula.getEstudiante().getCodigo());
            dto.setEstado(matricula.getEstado());
            dto.setNumeroSemestre(matricula.getSemestre().getNumero());
            dto.setNombreSemestre(matricula.getSemestre().getNombre());

            responseList.add(dto);
        }

        return responseList;
    }

    // Método privvado para convertir las matriculas en el modelo correspondiente de
    // DTO
    private MatriculaResponseDTO convertirAMatriculaResponseDTO(Matricula matricula) {
        MatriculaResponseDTO response = new MatriculaResponseDTO();
        response.setId(matricula.getId());
        response.setFechaMatricula(matricula.getFechaMatricula());
        response.setFechaCancelacion(matricula.getFechaCancelacion());
        response.setMotivoCancelacion(matricula.getMotivoCancelacion());
        response.setEstado(matricula.getEstado());

        Estudiante estudiante = matricula.getEstudiante();
        response.setEstudiante(new EstudianteMatriculaDTO(
                estudiante.getCedula(),
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getCorreo(),
                estudiante.getTelefono(),
                estudiante.getCodigo()));

        Semestre semestre = matricula.getSemestre();
        response.setSemestre(new SemestreMatriculaDTO(
                semestre.getId(),
                semestre.getNumero(),
                semestre.getNombre(),
                semestre.getAnio(),
                semestre.getFechaInicio(),
                semestre.getFechaFin()));

        ProgramaAcademico programaAcademico = matricula.getPrograma();
        response.setProgramaAcademico(new ProgramaAcademicoMatriculaDTO(
                programaAcademico.getNombre(),
                programaAcademico.getCodigo()));

        return response;
    }

    public List<MatriculaResumenEstudianteDTO> obtenerPorSemestre(Long semestreId) {
        List<Matricula> matriculas = matriculaRepository.findBySemestreId(semestreId);
        List<MatriculaResumenEstudianteDTO> responseList = new ArrayList<>();

        for (Matricula matricula : matriculas) {
            MatriculaResumenEstudianteDTO dto = new MatriculaResumenEstudianteDTO();
            dto.setId(matricula.getId());
            dto.setNombreEstudiante(matricula.getEstudiante().getNombre());
            dto.setCodigoEstudiante(matricula.getEstudiante().getCodigo());
            dto.setEstado(matricula.getEstado());
            dto.setNumeroSemestre(matricula.getSemestre().getNumero());
            dto.setNombreSemestre(matricula.getSemestre().getNombre());

            responseList.add(dto);
        }
        return responseList;
    }

    public List<MatriculaEstadoDTO> obtenerPorEstado(EstadoMatricula estado) {
        List<Matricula> matriculas = matriculaRepository.findByEstado(estado);
        List<MatriculaEstadoDTO> responseList = new ArrayList<>();

        for (Matricula matricula : matriculas) {
            MatriculaEstadoDTO dto = new MatriculaEstadoDTO();
            dto.setId(matricula.getId());
            dto.setEstudianteNombre(matricula.getEstudiante().getNombre());
            dto.setEstado(matricula.getEstado());
            dto.setFechaMatricula(matricula.getFechaMatricula());
            dto.setMotivoCancelacion(matricula.getMotivoCancelacion());

            responseList.add(dto);
        }

        return responseList;
    }

    @Transactional
    public MatriculaPrimeraVezResponseDTO matricularEstudiantePrimeraVez(MatriculaPrimeraVezRequestDTO dto) {

        Estudiante estudiante = estudianteRepository.findById(dto.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + dto.getEstudianteId()));

        Semestre semestre = semestreRepository.findById(dto.getSemestreId())
                .orElseThrow(() -> new RuntimeException("Semestre no encontrado con id: " + dto.getSemestreId()));

        ProgramaAcademico programaAcademico = programaAcademicoRepository.findById(dto.getProgramaId())
                .orElseThrow(() -> new RuntimeException(
                        "Programa académico no encontrado con id: " + dto.getProgramaId()));

        if (matriculaRepository.existsByEstudianteAndSemestre(estudiante, semestre)) {
            throw new IllegalStateException("El estudiante ya está matriculado en este semestre.");
        }

        if (semestre.getFechaFin().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("El semestre ya ha finalizado.");
        }

        if (estudiante.getProgramaAcademico() == null) {
            estudiante.setProgramaAcademico(programaAcademico);
        }
        estudiante.setProgramaAcademico(programaAcademico);
        estudianteRepository.save(estudiante);

        Matricula matricula = new Matricula();
        matricula.setEstudiante(estudiante);
        matricula.setSemestre(semestre);
        matricula.setPrograma(estudiante.getProgramaAcademico());
        matricula.setFechaMatricula(LocalDate.now());
        matricula.setEstado(EstadoMatricula.ACTIVA);
        matricula.setInscripciones(new ArrayList<>());

        matriculaRepository.save(matricula);

        MatriculaPrimeraVezResponseDTO response = new MatriculaPrimeraVezResponseDTO();
        response.setId(matricula.getId());
        response.setFechaMatricula(matricula.getFechaMatricula().toString());
        response.setEstado(matricula.getEstado().toString());
        response.setEstudianteId(estudiante.getId());
        response.setSemestreId(semestre.getId());
        response.setProgramaId(programaAcademico.getId());

        return response;
    }

    @Transactional
    public MatriculaEstudianteExistenteResponsetDTO matricularEstudiante(
            MatriculaEstudianteExistenteRequestDTO requestDTO) {

        // Obtener estudiante y semestre usando los IDs del DTO
        Estudiante estudiante = estudianteRepository.findById(requestDTO.getEstudianteId())
                .orElseThrow(() -> new IllegalStateException("Estudiante no encontrado."));
        Semestre semestre = semestreRepository.findById(requestDTO.getSemestreId())
                .orElseThrow(() -> new IllegalStateException("Semestre no encontrado."));

        // Validar que el estudiante tenga programa
        if (estudiante.getProgramaAcademico() == null) {
            throw new IllegalStateException("El estudiante no tiene un programa académico asignado.");
        }

        // Verificar si ya tiene matrícula activa en este semestre
        if (matriculaRepository.existsByEstudianteAndSemestreAndEstado(estudiante, semestre, EstadoMatricula.ACTIVA)) {
            throw new IllegalStateException("Ya existe una matrícula activa para este semestre.");
        }

        // Crear la matrícula
        Matricula matricula = new Matricula();
        matricula.setEstudiante(estudiante);
        matricula.setSemestre(semestre);
        matricula.setPrograma(estudiante.getProgramaAcademico());
        matricula.setFechaMatricula(LocalDate.now());
        matricula.setEstado(EstadoMatricula.ACTIVA);
        matricula.setInscripciones(new ArrayList<>());
        matriculaRepository.save(matricula);

        // Devolver el DTO con información detallada
        return new MatriculaEstudianteExistenteResponsetDTO(
                matricula.getId(),
                estudiante.getId(),
                estudiante.getNombre(),
                semestre.getId(),
                semestre.getNombre(),
                estudiante.getProgramaAcademico().getNombre(),
                matricula.getFechaMatricula(),
                matricula.getEstado().name());
    }

    public long contarCursosAprobados(long matriculaId) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByMatriculaId(matriculaId);
        return inscripciones.stream()
                .filter(inscripcion -> inscripcion.getEstado() == EstadoInscripcion.APROBADO)
                .count();
    }

    @Transactional
    public String cancelarMatricula(String motivo, long matriculaId) {
        Optional<Matricula> optionalMatricula = matriculaRepository.findById(matriculaId);

        if (optionalMatricula.isEmpty()) {
            return "No existe la matricula"; // No existe la matrícula
        }

        Matricula matricula = optionalMatricula.get();

        if (matricula.getEstado() != EstadoMatricula.ACTIVA) {
            return "Solo se pueden cancelar matriculas activas"; // Solo se pueden cancelar matrículas activas
        }

        // Actualizar estado y datos de cancelación
        matricula.setEstado(EstadoMatricula.CANCELADA);
        matricula.setFechaCancelacion(LocalDate.now());
        matricula.setMotivoCancelacion(motivo);

        // Cancelar todas las inscripciones inscritas asociadas
        if (matricula.getInscripciones() != null) {
            for (Inscripcion insc : matricula.getInscripciones()) {
                if (insc.getEstado() == EstadoInscripcion.INSCRITO) {
                    insc.setEstado(EstadoInscripcion.RETIRADO);
                }
            }
        }

        // Guardar cambios
        matriculaRepository.save(matricula);

        return matricula.getEstado().toString(); // Retornar el nuevo estado de la matrícula
    }

    @Transactional
    public ActualizarEstadoMatriculaResponseDTO actualizarEstadoMatricula(
            ActualizarEstadoMatriculaRequestDTO requestDTO) {

        // Buscar directamente en el repositorio
        Matricula matricula = matriculaRepository.findById(requestDTO.getMatriculaId())
                .orElseThrow(() -> new NoSuchElementException("Matrícula no encontrada"));

        // Guardar el estado anterior
        String estadoAnterior = matricula.getEstado().name();

        // Verificar si el estado anterior es CANCELADA y el nuevo estado es ACTIVA
        if (matricula.getEstado() == EstadoMatricula.CANCELADA
                && requestDTO.getNuevoEstado() == EstadoMatricula.ACTIVA) {
            // Establecer null en los campos de cancelación
            matricula.setFechaCancelacion(null);
            matricula.setMotivoCancelacion(null);
        }

        // Actualizar estado de la matrícula
        matricula.setEstado(requestDTO.getNuevoEstado());
        matriculaRepository.save(matricula);

        // Construir y devolver la respuesta
        return new ActualizarEstadoMatriculaResponseDTO(
                matricula.getId(),
                estadoAnterior,
                matricula.getEstado().name(),
                LocalDate.now());
    }

}