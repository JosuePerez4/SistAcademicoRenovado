package sistema.academico.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Asistencia;
import sistema.academico.entities.Estado;
import sistema.academico.entities.Estudiante;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findByEstado(Estado estado);
    List<Asistencia> findByEstudiante(Estudiante estudiante);
    List<Asistencia> findByFecha(Date fecha);
    List<Asistencia> findByEstudianteAndFecha(Estudiante estudiante, Date fecha);
}
