package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Semestre;

@Repository
public interface SemestreRepository extends JpaRepository<Semestre, Long> {
    Semestre findByNombre(String nombre); // Buscar un semestre por nombre
}
