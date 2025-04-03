package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.*;
import sistema.academico.repository.CursoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    // Crear un curso
    public Curso crearCurso(Curso curso) {
        if (curso.getClase() == null) {
            curso.setClase(new ArrayList<>());
        }
        if (curso.getEstudiante() == null) {
            curso.setEstudiante(new ArrayList<>());
        }
        return cursoRepository.save(curso);
    }

    // Modificar un curso
    public Curso modificarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    // Eliminar un curso por ID
    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    // Inscribir un estudiante en un curso
    public boolean inscribirEstudiante(Long idCurso, Estudiante estudiante) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isPresent()) {
            Curso curso = cursoOpt.get();
            if (curso.getEstudiante() == null) {
                curso.setEstudiante(new ArrayList<>());
            }
            if (curso.getEstudiante().size() < curso.getCupoMaximo()) {
                curso.getEstudiante().add(estudiante);
                cursoRepository.save(curso);
                return true;
            }
        }
        return false;
    }

    // Agregar un horario al curso
    public boolean agregarHorario(Long idCurso, Horario horario) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isPresent()) {
            Curso curso = cursoOpt.get();
            if (curso.getClase() == null) {
                curso.setClase(new ArrayList<>());
            }
            Clase nuevaClase = new Clase();
            nuevaClase.setHorario(horario);
            curso.getClase().add(nuevaClase);
            cursoRepository.save(curso);
            return true;
        }
        return false;
    }

    // Eliminar un horario del curso
    public boolean eliminarHorario(Long idCurso, Horario horario) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isPresent()) {
            Curso curso = cursoOpt.get();
            if (curso.getClase() != null) {
                curso.getClase().removeIf(clase -> clase.getHorario().equals(horario));
                cursoRepository.save(curso);
                return true;
            }
        }
        return false;
    }

    // Listar estudiantes inscritos en un curso
    public List<Estudiante> listarEstudiantes(Long idCurso) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        return cursoOpt.map(Curso::getEstudiante).orElse(new ArrayList<>());
    }

    // Listar horarios de un curso
    public List<Horario> listarHorarios(Long idCurso) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isPresent()) {
            Curso curso = cursoOpt.get();
            if (curso.getClase() != null) {
                return curso.getClase().stream()
                        .map(Clase::getHorario)
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    // Consultar disponibilidad del curso
    public boolean consultarDisponibilidad(Long idCurso, String dia, java.sql.Time hora) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isPresent()) {
            Curso curso = cursoOpt.get();
            if (curso.getClase() != null) {
                return curso.getClase().stream()
                        .anyMatch(clase -> clase.getHorario().getDiaSemana().equals(dia) &&
                                hora.after(clase.getHorario().getHoraInicio()) &&
                                hora.before(clase.getHorario().getHoraFin()));
            }
        }
        return false;
    }
}
