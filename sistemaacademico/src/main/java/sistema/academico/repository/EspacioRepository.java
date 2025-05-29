package sistema.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Espacio;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long>{
    List<Espacio> findByDisponibleTrue();
}
