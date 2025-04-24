package sistema.academico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Calificacion;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    //Optional<Calificacion> findByEvaluacionIdAndCursoId(Long evaluacionId, Long cursoId);
}
