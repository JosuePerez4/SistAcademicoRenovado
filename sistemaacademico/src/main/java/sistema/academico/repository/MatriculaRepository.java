package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.Matricula;
import sistema.academico.entities.Semestre;
import sistema.academico.enums.EstadoMatricula;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    boolean existsByEstudianteAndSemestre(Estudiante estudiante, Semestre semestre);

    List<Matricula> findBySemestreId(long semestreId);

    List<Matricula> findByEstado(EstadoMatricula estado);

    Optional<Matricula> findByEstudianteIdAndEstado(Long estudianteId, EstadoMatricula estado);

    boolean existsByEstudianteAndSemestreAndEstado(Estudiante estudiante, Semestre semestre, EstadoMatricula estado);

    Optional<Matricula> findByEstudianteIdAndSemestreId(Long estudianteId, Long semestreId);

    List<Matricula> findByEstudianteId(Long estudianteId);

    @Query("SELECT m FROM Matricula m WHERE m.estudiante.id = :id ORDER BY m.fechaMatricula DESC")
    List<Matricula> findByEstudianteIdOrderByFechaMatriculaDesc(@Param("id") Long id);
}
