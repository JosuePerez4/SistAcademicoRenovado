package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Calificacion;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
}
