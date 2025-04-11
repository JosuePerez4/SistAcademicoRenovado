package sistema.academico.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
public class MatriculaService {
    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    public Matricula obtenerMatriculaPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Matrícula no encontrada"));
    }

    public List<Matricula> obtenerTodas() {
        return matriculaRepository.findAll();
    }

    public List<Matricula> obtenerPorEstudiante(Long estudianteId) {
        return matriculaRepository.findByEstudianteId(estudianteId);
    }

    public List<Matricula> obtenerPorSemestre(Long semestreId) {
        return matriculaRepository.findBySemestreId(semestreId);
    }

    public List<Matricula> obtenerPorEstado(EstadoMatricula estado) {
        return matriculaRepository.findByEstado(estado);
    }

    @Transactional
    public Matricula matricularEstudiantePrimeraVez(Estudiante estudiante, Semestre semestre,
            ProgramaAcademico programaAcademico) {

        if (matriculaRepository.existsByEstudianteAndSemestre(estudiante, semestre)) {
            throw new IllegalStateException("El estudiante ya está matriculado en este semestre.");
        }

        if (semestre.getFechaFin().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("El semestre ya ha finalizado.");
        }

        if (estudiante.getProgramaAcademico() == null) {
            if (programaAcademico == null) {
                throw new IllegalArgumentException("Debe proporcionar un programa académico para nuevos estudiantes.");
            }
            estudiante.setProgramaAcademico(programaAcademico);
        }

        estudianteRepository.save(estudiante);

        Matricula matricula = new Matricula();
        matricula.setEstudiante(estudiante);
        matricula.setSemestre(semestre);
        matricula.setPrograma(programaAcademico != null ? programaAcademico : estudiante.getProgramaAcademico());
        matricula.setFechaMatricula(LocalDate.now());
        matricula.setEstado(EstadoMatricula.ACTIVA);
        matricula.setInscripciones(new ArrayList<>());

        return matriculaRepository.save(matricula); // devolvemos la matrícula persistida
    }

    @Transactional
    public boolean matricularEstudiante(Estudiante estudiante, Semestre semestre, List<Curso> cursos) {
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

        return true;
    }

    public double calcularPromedio(long matriculaId) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByMatriculaId(matriculaId);
        return inscripciones.stream()
                .filter(i -> i.getNotaFinal() != null)
                .mapToDouble(Inscripcion::getNotaFinal)
                .average()
                .orElse(0.0);
    }

    public long contarCursosAprobados(long matriculaId) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByMatriculaId(matriculaId);
        return inscripciones.stream()
                .filter(inscripcion -> inscripcion.getEstado() == EstadoInscripcion.APROBADO)
                .count();
    }

    @Transactional
    public boolean cancelarMatricula(String motivo, long matriculaId) {
        Optional<Matricula> optionalMatricula = matriculaRepository.findById(matriculaId);

        if (optionalMatricula.isEmpty()) {
            return false; // No existe la matrícula
        }

        Matricula matricula = optionalMatricula.get();

        if (matricula.getEstado() != EstadoMatricula.ACTIVA) {
            return false; // Solo se pueden cancelar matrículas activas
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

        return true;
    }

    public boolean actualizarEstadoMatricula(Long matriculaId, EstadoMatricula nuevoEstado) {
        Matricula matricula = obtenerMatriculaPorId(matriculaId);
        matricula.setEstado(nuevoEstado);
        matriculaRepository.save(matricula);
        return true;
    }

}