package sistema.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.HistorialAcademico;
import sistema.academico.entities.Matricula;

@Repository
public interface HistorialAcademicoRepository extends JpaRepository<HistorialAcademico, Long> {
    List<HistorialAcademico> findByEstudianteId(Long id);

    Matricula findMatriculaByEstudianteId(Long estudianteId);
}