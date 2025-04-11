package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Reporte;
import sistema.academico.entities.Usuario;

import java.util.Date;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByTitulo(String titulo); // Buscar por título
    List<Reporte> findByTipo(String tipo); // Buscar por tipo
    List<Reporte> findByFechaGeneracionBetween(Date inicio, Date fin); // Búsqueda por rango de fechas
}
