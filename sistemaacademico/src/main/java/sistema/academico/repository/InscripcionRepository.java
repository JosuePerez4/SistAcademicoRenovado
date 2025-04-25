package sistema.academico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Curso;
import sistema.academico.entities.Inscripcion;
import sistema.academico.enums.EstadoInscripcion;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByMatriculaId(Long matriculaId);

    boolean existsByMatriculaIdAndCursoId(Long matriculaId, Long cursoId);

    long countByMatriculaIdAndEstado(Long matriculaId, EstadoInscripcion estado);

    List<Inscripcion> findByCursoIdAndEstado(Long cursoId, EstadoInscripcion estado);

    long countByCursoIdAndEstado(Long cursoId, EstadoInscripcion estado);

    Optional<Inscripcion> findByMatriculaIdAndCursoId(Long matriculaId, Long cursoId);

    List<Inscripcion> findByCursoId(Long cursoId);

    List<Inscripcion> findByCurso(Curso curso);
}
