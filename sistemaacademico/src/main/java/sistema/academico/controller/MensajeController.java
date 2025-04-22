package sistema.academico.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema.academico.DTO.EnviarMensajeRequestDTO;
import sistema.academico.DTO.EnviarMensajeResponseDTO;
import sistema.academico.DTO.MensajeResponseDTO;
import sistema.academico.entities.Mensaje;
import sistema.academico.services.MensajeService;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @PostMapping("/enviar")
    public ResponseEntity<EnviarMensajeResponseDTO> enviarMensaje(@RequestBody EnviarMensajeRequestDTO requestDTO) {
        try {
            Mensaje mensajeEnviado = mensajeService.enviarMensaje(
                    requestDTO.getRemitenteId(),
                    requestDTO.getDestinatarioId(),
                    requestDTO.getAsunto(),
                    requestDTO.getContenido()
            );
            if (mensajeEnviado != null) {
                EnviarMensajeResponseDTO responseDTO = new EnviarMensajeResponseDTO();
                responseDTO.setMensajeId(mensajeEnviado.getId());
                responseDTO.setMensaje("Mensaje enviado con éxito");
                return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recibidos/{usuarioId}")
    public ResponseEntity<List<MensajeResponseDTO>> listarMensajesRecibidos(@PathVariable Long usuarioId) {
        try {
            List<Mensaje> mensajes = mensajeService.listarMensajesRecibidos(usuarioId);
            List<MensajeResponseDTO> responseDTOs = mensajes.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/enviados/{usuarioId}")
    public ResponseEntity<List<MensajeResponseDTO>> listarMensajesEnviados(@PathVariable Long usuarioId) {
        try {
            List<Mensaje> mensajes = mensajeService.listarMensajesEnviados(usuarioId);
            List<MensajeResponseDTO> responseDTOs = mensajes.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/leer/{mensajeId}")
    public ResponseEntity<String> marcarComoLeido(@PathVariable Long mensajeId) {
        try {
            mensajeService.marcarComoLeido(mensajeId);
            return new ResponseEntity<>("Mensaje marcado como leído", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{mensajeId}")
    public ResponseEntity<String> eliminarMensaje(@PathVariable Long mensajeId) {
        try {
            mensajeService.eliminarMensaje(mensajeId);
            return new ResponseEntity<>("Mensaje eliminado con éxito", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{mensajeId}")
    public ResponseEntity<MensajeResponseDTO> obtenerDetallesMensaje(@PathVariable Long mensajeId) {
        try {
            Mensaje mensaje = mensajeService.obtenerDetallesMensaje(mensajeId);
            if (mensaje != null) {
                return new ResponseEntity<>(convertToResponseDTO(mensaje), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private MensajeResponseDTO convertToResponseDTO(Mensaje mensaje) {
        MensajeResponseDTO dto = new MensajeResponseDTO();
        dto.setId(mensaje.getId());
        dto.setRemitenteId(mensaje.getRemitente().getId());
        dto.setRemitenteNombreCompleto(mensaje.getRemitente().getNombre() + " " + mensaje.getRemitente().getApellido());
        dto.setDestinatarioId(mensaje.getDestinatario().getId());
        dto.setDestinatarioNombreCompleto(mensaje.getDestinatario().getNombre() + " " + mensaje.getDestinatario().getApellido());
        dto.setAsunto(mensaje.getAsunto());
        dto.setContenido(mensaje.getContenido());
        dto.setFechaEnvio(mensaje.getFechaEnvio());
        dto.setLeido(mensaje.isLeido());
        return dto;
    }
}