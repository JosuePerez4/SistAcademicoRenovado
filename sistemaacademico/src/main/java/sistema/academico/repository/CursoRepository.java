package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Curso;
import sistema.academico.entities.Materia;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByNombre(String nombre);
    List<Curso> findByCodigo(String codigo);
    List<Curso> findByMateria(Materia materia);
    List<Curso> findBySemestre(int semestre);
    List<Curso> findByCupoMaximoLessThanEqual(int cupoMaximo); // Buscar cursos con cupo disponible
}
