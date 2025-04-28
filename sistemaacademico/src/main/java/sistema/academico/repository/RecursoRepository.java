package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.academico.entities.Recurso;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {
}
