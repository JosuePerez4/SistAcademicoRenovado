package sistema.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema.academico.DTO.ReservaEspacioDTO;
import sistema.academico.services.ReservaEspacioService;

@RestController
@RequestMapping("/api/reservaEspacios")
public class ReservaEspacioController {

    @Autowired
    private ReservaEspacioService reservaEspacioService;

    /**
     * Endpoint para reservar un espacio.
     * 
     * Ruta: POST /api/reservaEspacios
     * 
     * @param reservaEspacioDTO el objeto con los datos de la reserva
     * @return ResponseEntity con el estado de la operación
     */
    @PostMapping("/reservar")
    public ResponseEntity<String> reservarEspacio(@RequestBody ReservaEspacioDTO reservaEspacioDTO) {
        boolean exito = reservaEspacioService.reservarEspacio(reservaEspacioDTO);
        if (exito) {
            return new ResponseEntity<>("Reserva realizada con éxito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("El espacio ya está reservado en ese horario", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint para eliminar una reserva existente.
     * 
     * Ruta: DELETE /api/reservaEspacios/{reservaId}
     * 
     * @param reservaId el ID de la reserva a eliminar
     * @return ResponseEntity con el estado de la operación
     */
    @DeleteMapping("/eliminarReserva/{reservaId}")
    public ResponseEntity<String> eliminarReserva(@PathVariable Long reservaId) {
        boolean exito = reservaEspacioService.eliminarReserva(reservaId);
        if (exito) {
            return new ResponseEntity<>("Reserva eliminada con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró la reserva", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para obtener todas las reservas de un espacio.
     * 
     * Ruta: GET /api/reservaEspacios/espacio/{espacioId}
     * 
     * @param espacioId el ID del espacio para obtener sus reservas
     * @return ResponseEntity con la lista de reservas para el espacio
     */
    @GetMapping("/espacio/{espacioId}")
    public ResponseEntity<List<ReservaEspacioDTO>> obtenerReservasPorEspacio(@PathVariable Long espacioId) {
        List<ReservaEspacioDTO> reservas = reservaEspacioService.obtenerReservasPorEspacio(espacioId);
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener todas las reservas de un curso.
     * 
     * Ruta: GET /api/reservaEspacios/curso/{cursoId}
     * 
     * @param cursoId el ID del curso para obtener sus reservas
     * @return ResponseEntity con la lista de reservas para el curso
     */
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<ReservaEspacioDTO>> obtenerReservasPorCurso(@PathVariable Long cursoId) {
        List<ReservaEspacioDTO> reservas = reservaEspacioService.obtenerReservasPorCurso(cursoId);
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    /**
     * Endpoint para actualizar una reserva existente.
     * 
     * Ruta: PUT /api/reservaEspacios/{reservaId}
     * 
     * @param reservaId         el ID de la reserva a actualizar
     * @param reservaEspacioDTO los nuevos datos para la reserva
     * @return ResponseEntity con el estado de la operación
     */
    @PutMapping("/actualizarReserva/{reservaId}")
    public ResponseEntity<String> actualizarReserva(@PathVariable Long reservaId,
            @RequestBody ReservaEspacioDTO reservaEspacioDTO) {
        boolean exito = reservaEspacioService.actualizarReserva(reservaId, reservaEspacioDTO);
        if (exito) {
            return new ResponseEntity<>("Reserva actualizada con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se pudo actualizar la reserva debido a un conflicto de horarios",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
