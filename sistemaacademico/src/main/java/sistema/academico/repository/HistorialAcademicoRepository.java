package sistema.academico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.HistorialAcademico;
import sistema.academico.entities.Matricula;

@Repository
public interface HistorialAcademicoRepository extends JpaRepository<HistorialAcademico, Long> {
    @Query("SELECT h FROM HistorialAcademico h WHERE h.estudiante.id = :id")
List<HistorialAcademico> findByEstudianteId(@Param("id") Long id);

    //Optional<HistorialAcademico> findByEstudianteId(Long estudianteId);
    Matricula findMatriculaByEstudianteId(Long estudianteId);
}