package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.academico.entities.Docente;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
}
