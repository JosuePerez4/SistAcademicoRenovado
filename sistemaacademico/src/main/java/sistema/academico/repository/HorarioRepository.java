package sistema.academico.repository;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
    Optional<Horario> findByDiaSemanaAndHoraInicioAndAula(String diaSemana, Time horaInicio, String aula);
    List<Horario> findByAula(String aula);
    List<Horario> findByCursoId(Long cursoId);
}
