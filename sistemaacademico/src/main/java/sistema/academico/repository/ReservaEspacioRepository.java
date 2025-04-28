package sistema.academico.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Espacio;
import sistema.academico.entities.ReservaEspacio;

@Repository
public interface ReservaEspacioRepository extends JpaRepository<ReservaEspacio, Long> {
    List<ReservaEspacio> findByEspacioAndFecha(Espacio espacio, LocalDate fecha);
    List<ReservaEspacio> findByEspacioId(Long espacioId);
    List<ReservaEspacio> findByCursoId(Long cursoId);
}
