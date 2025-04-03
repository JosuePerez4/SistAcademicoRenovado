package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Calificacion;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Estudiante;

import java.util.Date;
import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByEstudiante(Estudiante estudiante);
    List<Calificacion> findByCurso(Curso curso);
    List<Calificacion> findByFechaRegistro(Date fechaRegistro);
    List<Calificacion> findByEstudianteAndCurso(Estudiante estudiante, Curso curso);
    List<Calificacion> findByTipoEvaluacion(String tipoEvaluacion);
}
