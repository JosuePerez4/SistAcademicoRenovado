package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Evaluacion;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    
}