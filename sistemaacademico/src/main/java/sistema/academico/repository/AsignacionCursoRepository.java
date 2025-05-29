package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.AsignacionCurso;

import java.util.List;
@Repository
public interface AsignacionCursoRepository extends JpaRepository<AsignacionCurso, Long> {
    List<AsignacionCurso> findByDocenteId(Long docenteId);
}
