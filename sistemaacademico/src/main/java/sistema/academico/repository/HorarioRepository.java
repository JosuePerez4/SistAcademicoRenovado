package sistema.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Espacio;
import sistema.academico.entities.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByCursoId(Long cursoId);

    // Buscar horarios por día de la semana
    List<Horario> findByDiaSemana(String diaSemana);

    List<Horario> findByEspacioAndDiaSemana(Espacio espacio, String diaSemana);
}
