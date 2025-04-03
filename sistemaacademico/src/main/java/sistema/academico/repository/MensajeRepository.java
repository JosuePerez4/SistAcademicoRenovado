package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long>{
    
}
