
package sistema.academico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.academico.entities.Curso;
import sistema.academico.repository.CursoRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public Curso crearCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso modificarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    public List<Curso> listarCursosPorMateria(Long materiaId) {
        return cursoRepository.findByMateriaId(materiaId);
    }

    public List<Curso> listarTodosLosCursos() {
        return cursoRepository.findAll();
    }

    public List<Curso> obtenerCursosDisponiblesPorMateria(Long materiaId) {
        List<Curso> cursos = cursoRepository.findByMateriaId(materiaId);

        return cursos.stream()
                .filter(c -> c.getInscripciones().size() < c.getCupoMaximo())
                .toList();
    }

    /*
     * Inscribir un estudiante en un curso
     * public boolean inscribirEstudiante(Long idCurso, Estudiante estudiante) {
     * Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
     * if (cursoOpt.isPresent()) {
     * Curso curso = cursoOpt.get();
     * if (curso.getEstudiantes() == null) {
     * curso.setEstudiantes(new ArrayList<>());
     * }
     * if (curso.getEstudiantes().size() < curso.getCupoMaximo()) {
     * curso.getEstudiantes().add(estudiante);
     * cursoRepository.save(curso);
     * return true;
     * }
     * }
     * return false;
     * }
     */

    /*
     * Agregar una clase al curso
     * public boolean agregarClase(Long idCurso, Clase nuevaClase) {
     * Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
     * if (cursoOpt.isPresent()) {
     * Curso curso = cursoOpt.get();
     * if (curso.getClases() == null) {
     * curso.setClases(new ArrayList<>());
     * }
     * curso.getClases().add(nuevaClase);
     * cursoRepository.save(curso);
     * return true;
     * }
     * return false;
     * }
     */

    /*
     * Eliminar una clase del curso
     * public boolean eliminarClase(Long idCurso, Clase claseAEliminar) {
     * Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
     * if (cursoOpt.isPresent()) {
     * Curso curso = cursoOpt.get();
     * if (curso.getClases() != null) {
     * curso.getClases().removeIf(clase -> clase.equals(claseAEliminar));
     * cursoRepository.save(curso);
     * return true;
     * }
     * }
     * return false;
     * }
     */

    /*
     * Listar estudiantes inscritos en un curso
     * public List<Estudiante> listarEstudiantes(Long idCurso) {
     * Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
     * return cursoOpt.map(Curso::getEstudiantes).orElse(new ArrayList<>());
     * }
     */

    /*
     * Listar horarios de un curso
     * public List<Horario> listarHorarios(Long idCurso) {
     * Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
     * if (cursoOpt.isPresent()) {
     * Curso curso = cursoOpt.get();
     * if (curso.getClases() != null) {
     * return curso.getClases().stream()
     * .map(Clase::getHorario)
     * .collect(Collectors.toList());
     * }
     * }
     * return new ArrayList<>();
     * }
     */

    /*
     * Consultar disponibilidad del curso
     * public boolean consultarDisponibilidad(Long idCurso, String dia,
     * java.sql.Time hora) {
     * Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
     * if (cursoOpt.isPresent()) {
     * Curso curso = cursoOpt.get();
     * if (curso.getClases() != null) {
     * return curso.getClases().stream()
     * .anyMatch(clase -> clase.getHorario().getDiaSemana().equals(dia) &&
     * hora.after(clase.getHorario().getHoraInicio()) &&
     * hora.before(clase.getHorario().getHoraFin()));
     * }
     * }
     * return false;
     * }
     */

}