package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.EstadoMatricula;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.Matricula;
import sistema.academico.entities.Semestre;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    boolean existsByEstudianteAndSemestre(Estudiante estudiante, Semestre semestre);

    List<Matricula> findByEstudianteId(long estudianteId);

    List<Matricula> findBySemestreId(long semestreId);

    List<Matricula> findByEstado(EstadoMatricula estado);

    Optional<Matricula> findByEstudianteIdAndEstado(Long estudianteId, EstadoMatricula estado);

    boolean existsByEstudianteAndSemestreAndEstado(Estudiante estudiante, Semestre semestre, EstadoMatricula estado);
}
