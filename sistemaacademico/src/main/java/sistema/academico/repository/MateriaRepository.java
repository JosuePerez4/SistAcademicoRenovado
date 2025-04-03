package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Materia;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long>{
    
}
