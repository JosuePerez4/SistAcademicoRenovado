package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Clase;
import sistema.academico.entities.Horario;

import java.util.Date;
import java.util.List;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {
    List<Clase> findByFecha(Date fecha);
    List<Clase> findByHorario(Horario horario);
    List<Clase> findByAsistencia_Id(Long asistenciaId); // Buscar por ID de asistencia
    List<Clase> findByFechaAndHorario(Date fecha, Horario horario); // Buscar combinando atributos
}
