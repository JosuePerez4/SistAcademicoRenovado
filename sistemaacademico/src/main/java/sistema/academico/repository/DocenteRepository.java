package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Docente;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    
}
