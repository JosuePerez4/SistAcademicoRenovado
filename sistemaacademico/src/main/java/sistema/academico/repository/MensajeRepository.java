package sistema.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.academico.entities.Mensaje;
import sistema.academico.entities.Usuario;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long>{
    List<Mensaje> findByDestinatario(Usuario destinatario);
    List<Mensaje> findByRemitente(Usuario remitente);
}
