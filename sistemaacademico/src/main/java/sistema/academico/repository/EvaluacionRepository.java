package sistema.academico.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Curso;
import sistema.academico.entities.Evaluacion;
import sistema.academico.enums.TipoEvaluacion;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    boolean existsByCursoAndTipo(Curso curso, TipoEvaluacion tipo);
    List<Evaluacion> findByFechaLimite(LocalDate fechaLimite);
}