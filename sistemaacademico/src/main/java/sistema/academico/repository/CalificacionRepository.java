package sistema.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Calificacion;
import sistema.academico.entities.Estudiante;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    //List<Calificacion> findByEstudiante(Estudiante estudiante);
}
