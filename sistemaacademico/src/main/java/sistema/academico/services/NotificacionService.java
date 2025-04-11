package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Mensaje;
import sistema.academico.entities.Notificacion;
import sistema.academico.entities.Usuario;
import sistema.academico.repository.NotificacionRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    /*  Crear una nueva notificación
    public Notificacion crearNotificacion(String destinatario, Mensaje mensaje, Usuario usuarioDestino) {
        Notificacion notificacion = new Notificacion();
        notificacion.setDestinatario(destinatario);
        notificacion.setMensaje(mensaje);
        notificacion.setUsuarioDestino(usuarioDestino);
        notificacion.setFechaCreacion(new Date()); // Establecer fecha actual
        return notificacionRepository.save(notificacion);
    }*/

    // Obtener todas las notificaciones
    public List<Notificacion> obtenerTodasNotificaciones() {
        return notificacionRepository.findAll();
    }

    // Enviar notificación (actualizar estado de envío)
    public boolean enviarNotificacion(Long idNotificacion) {
        Optional<Notificacion> notificacionOpt = notificacionRepository.findById(idNotificacion);
        if (notificacionOpt.isPresent()) {
            Notificacion notificacion = notificacionOpt.get();
            // Aquí agregarías la lógica real para enviar la notificación (por correo, SMS, etc.)
        }
        return false;
    }

    // Eliminar notificación por ID
    public boolean eliminarNotificacion(Long idNotificacion) {
        Optional<Notificacion> notificacionOpt = notificacionRepository.findById(idNotificacion);
        if (notificacionOpt.isPresent()) {
            notificacionRepository.deleteById(idNotificacion);
            return true;
        }
        return false;
    }
}