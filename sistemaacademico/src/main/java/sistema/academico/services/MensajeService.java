package sistema.academico.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import sistema.academico.entities.Mensaje;
import sistema.academico.entities.Usuario;
import sistema.academico.repository.MensajeRepository;

public class MensajeService {
    
    @Autowired
    private MensajeRepository mensajeRepository;

    // Enviar mensaje a un usuario
    public boolean enviarMensaje(Usuario destinatario, String contenido) {
        if (destinatario == null || contenido == null || contenido.isEmpty()) {
            return false;
        }
        Mensaje mensaje = new Mensaje();
        mensaje.setDestinatario(destinatario);
        mensaje.setContenido(contenido);
        mensaje.setLeido(false);
        mensajeRepository.save(mensaje);
        return true;
    }

    // Marcar un mensaje como leído
    public void marcarComoLeido(Long idMensaje) {
        Optional<Mensaje> mensajeOpt = mensajeRepository.findById(idMensaje);
        if (mensajeOpt.isPresent()) {
            Mensaje mensaje = mensajeOpt.get();
            mensaje.setLeido(true);
            mensajeRepository.save(mensaje);
        } else {
            throw new RuntimeException("Mensaje no encontrado");
        }
    }

    // Eliminar un mensaje
    public void eliminarMensaje(Long idMensaje) {
        if (mensajeRepository.existsById(idMensaje)) {
            mensajeRepository.deleteById(idMensaje);
        } else {
            throw new RuntimeException("Mensaje no encontrado");
        }
    }

    // Obtener detalles de un mensaje
    public String obtenerDetallesMensaje(Long idMensaje) {
        Optional<Mensaje> mensajeOpt = mensajeRepository.findById(idMensaje);
        if (mensajeOpt.isPresent()) {
            Mensaje mensaje = mensajeOpt.get();
            return "De: " + mensaje.getRemitente().getNombre() +
                   "\nPara: " + mensaje.getDestinatario().getNombre() +
                   "\nContenido: " + mensaje.getContenido() +
                   "\nLeído: " + (mensaje.isLeido() ? "Sí" : "No");
        }
        throw new RuntimeException("Mensaje no encontrado");
    }
}
