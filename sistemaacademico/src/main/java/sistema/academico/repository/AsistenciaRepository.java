package sistema.academico.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Asistencia;
import sistema.academico.entities.Inscripcion;
import sistema.academico.enums.AsistenciaEstado;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {


    List<Asistencia> findByEstado(AsistenciaEstado estado);

    List<Asistencia> findByInscripcion(Inscripcion inscripcion);

    List<Asistencia> findByInscripcionId(Long inscripcionId);

    List<Asistencia> findByInscripcionAndFecha(Inscripcion inscripcion, LocalDate fecha);

    List<Asistencia> findByFecha(LocalDate fecha);

    List<Asistencia> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<Asistencia> findByInscripcionAndFechaBetween(Inscripcion inscripcion, LocalDate fechaInicio,
            LocalDate fechaFin);

    List<Asistencia> findByInscripcionAndEstado(Inscripcion inscripcion, AsistenciaEstado estado);

    boolean existsByInscripcionAndFecha(Inscripcion inscripcion, LocalDate fecha);

    List<Asistencia> findByInscripcionCursoIdAndEstado(Long cursoId, AsistenciaEstado estado);

}
