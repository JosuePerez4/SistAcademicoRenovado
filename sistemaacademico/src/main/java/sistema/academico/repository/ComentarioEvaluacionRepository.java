package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.academico.entities.ComentarioEvaluacion;

import java.util.List;

public interface ComentarioEvaluacionRepository extends JpaRepository<ComentarioEvaluacion, Long> {
    List<ComentarioEvaluacion> findByEvaluacionId(Long evaluacionId);
}
