package sistema.academico.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Mensaje;
import sistema.academico.entities.Usuario;
import sistema.academico.repository.MensajeRepository;
import sistema.academico.repository.UsuarioRepository;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Enviar mensaje a un usuario
    public Mensaje enviarMensaje(Long remitenteId, Long destinatarioId, String asunto, String contenido) {
        if (remitenteId == null || destinatarioId == null || contenido == null || contenido.isEmpty()) {
            return null; // O podrías lanzar una excepción más específica
        }

        Optional<Usuario> remitenteOpt = usuarioRepository.findById(remitenteId);
        Optional<Usuario> destinatarioOpt = usuarioRepository.findById(destinatarioId);

        if (remitenteOpt.isPresent() && destinatarioOpt.isPresent()) {
            Usuario remitente = remitenteOpt.get();
            Usuario destinatario = destinatarioOpt.get();

            Mensaje mensaje = new Mensaje();
            mensaje.setRemitente(remitente);
            mensaje.setDestinatario(destinatario);
            mensaje.setAsunto(asunto);
            mensaje.setContenido(contenido);
            mensaje.setFechaEnvio(new Date());
            mensaje.setLeido(false);
            return mensajeRepository.save(mensaje);
        } else {
            throw new RuntimeException("No se encontró el remitente o el destinatario");
        }
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
    public Mensaje obtenerDetallesMensaje(Long idMensaje) {
        Optional<Mensaje> mensajeOpt = mensajeRepository.findById(idMensaje);
        if (mensajeOpt.isPresent()) {
            return mensajeOpt.get();
        }
        throw new RuntimeException("Mensaje no encontrado");
    }

    // Listar mensajes recibidos por un usuario
    public List<Mensaje> listarMensajesRecibidos(Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario destinatario = usuarioOpt.get();
            return mensajeRepository.findByDestinatario(destinatario);
        } else {
            throw new RuntimeException("No se encontró el usuario con ID: " + usuarioId);
        }
    }

    // Listar mensajes enviados por un usuario
    public List<Mensaje> listarMensajesEnviados(Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario remitente = usuarioOpt.get();
            return mensajeRepository.findByRemitente(remitente);
        } else {
            throw new RuntimeException("No se encontró el usuario con ID: " + usuarioId);
        }
    }
}