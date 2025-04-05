package sistema.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Notificacion;
import sistema.academico.entities.Usuario;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByDestinatario(String destinatario);
    List<Notificacion> findByUsuarioDestino(Usuario usuarioDestino);
    List<Notificacion> findByFechaCreacionBetween(Date inicio, Date fin); // Búsqueda por rango de fechas
}
