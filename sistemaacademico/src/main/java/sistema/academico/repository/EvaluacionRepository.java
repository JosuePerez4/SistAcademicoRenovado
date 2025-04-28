package sistema.academico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Curso;
import sistema.academico.entities.Evaluacion;
import sistema.academico.enums.TipoEvaluacion;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    Optional<Evaluacion> findByCursoIdAndTipo(Long cursoId, TipoEvaluacion tipo);
    boolean existsByCursoAndTipo(Curso curso, TipoEvaluacion tipo);

}