package sistema.academico.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import sistema.academico.DTO.ReservaEspacioDTO;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Espacio;
import sistema.academico.entities.ReservaEspacio;
import sistema.academico.repository.*;

@Service
public class ReservaEspacioService {

    @Autowired
    private ReservaEspacioRepository reservaEspacioRepository;

    @Autowired
    private EspacioRepository espacioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public boolean reservarEspacio(ReservaEspacioDTO reservaEspacioDTO) {
        Espacio espacio = espacioRepository.findById(reservaEspacioDTO.getEspacioId())
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        // Buscar reservas existentes para ese espacio y fecha
        List<ReservaEspacio> reservas = reservaEspacioRepository.findByEspacioAndFecha(espacio,
                reservaEspacioDTO.getFecha());
        LocalDate fecha = reservaEspacioDTO.getFecha();
        LocalTime horaInicio = LocalTime.parse(reservaEspacioDTO.getHoraInicio());
        LocalTime horaFin = LocalTime.parse(reservaEspacioDTO.getHoraFin());
        String motivo = reservaEspacioDTO.getMotivo();
        // Verificar si el curso existe buscando por ID
        Curso curso = reservaEspacioDTO.getCursoId() != null ? cursoRepository.findById(reservaEspacioDTO.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado")) : null;
        // Verificar si hay un choque de horarios
        for (ReservaEspacio reserva : reservas) {
            if (horaInicio.isBefore(reserva.getHoraFin()) && horaFin.isAfter(reserva.getHoraInicio())) {
                // Hay conflicto de horarios
                return false;
            }
        }

        // Si no hay conflicto, crear reserva
        ReservaEspacio nuevaReserva = new ReservaEspacio();
        nuevaReserva.setEspacio(espacio);
        nuevaReserva.setFecha(fecha);
        nuevaReserva.setHoraInicio(horaInicio);
        nuevaReserva.setHoraFin(horaFin);
        nuevaReserva.setMotivo(motivo);
        nuevaReserva.setCurso(curso);

        reservaEspacioRepository.save(nuevaReserva);

        return true;
    }

    public boolean eliminarReserva(Long reservaId) {
        ReservaEspacio reserva = reservaEspacioRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reservaEspacioRepository.delete(reserva);
        return true;
    }

    public List<ReservaEspacioDTO> obtenerReservasPorEspacio(Long espacioId) {
        // Verificar si el espacio existe y si existe, crear la lista de reservas
        if (!espacioRepository.existsById(espacioId)) {
            throw new RuntimeException("Espacio no encontrado");
        }
        List<ReservaEspacio> reservas = reservaEspacioRepository.findByEspacioId(espacioId);

        List<ReservaEspacioDTO> reservasDTO = new ArrayList<>();
        for (ReservaEspacio reserva : reservas) {
            ReservaEspacioDTO reservaDTO = new ReservaEspacioDTO();
            reservaDTO.setEspacioId(reserva.getEspacio().getId());
            reservaDTO.setFecha(reserva.getFecha());
            reservaDTO.setHoraInicio(reserva.getHoraInicio().toString());
            reservaDTO.setHoraFin(reserva.getHoraFin().toString());
            reservaDTO.setMotivo(reserva.getMotivo());
            if (reserva.getCurso() != null) {
                reservaDTO.setCursoId(reserva.getCurso().getId());
            }
            reservasDTO.add(reservaDTO);
        }
        return reservasDTO;
    }

    public List<ReservaEspacioDTO> obtenerReservasPorCurso(Long cursoId) {
        // Verificar si el curso existe y si existe, crear la lista de reservas
        if (!cursoRepository.existsById(cursoId)) {
            throw new RuntimeException("Curso no encontrado");
        }
        List<ReservaEspacio> reservas = reservaEspacioRepository.findByCursoId(cursoId);
        List<ReservaEspacioDTO> reservasDTO = new ArrayList<>();
        for (ReservaEspacio reserva : reservas) {
            ReservaEspacioDTO reservaDTO = new ReservaEspacioDTO();
            reservaDTO.setEspacioId(reserva.getEspacio().getId());
            reservaDTO.setFecha(reserva.getFecha());
            reservaDTO.setHoraInicio(reserva.getHoraInicio().toString());
            reservaDTO.setHoraFin(reserva.getHoraFin().toString());
            reservaDTO.setMotivo(reserva.getMotivo());
            if (reserva.getCurso() != null) {
                reservaDTO.setCursoId(reserva.getCurso().getId());
            }
            reservasDTO.add(reservaDTO);
        }
        return reservasDTO;
    }

    public boolean actualizarReserva(Long reservaId, ReservaEspacioDTO reservaEspacioDTO) {
        ReservaEspacio reserva = reservaEspacioRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // Verificar la disponibilidad del nuevo espacio y horario, creando un nuevo objeto
        // ReservaEspacioDTO para la verificación

        // Verificar si el espacio existe
        Espacio espacio = espacioRepository.findById(reservaEspacioDTO.getEspacioId())
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));
        // Crear un nuevo objeto ReservaEspacioDTO para la verificación
        reservaEspacioDTO.setEspacioId(espacio.getId());
        if (!verificarDisponibilidad(reservaEspacioDTO)) {
            return false; // No se puede actualizar la reserva debido a un conflicto
        }

        // Actualizar los valores de la reserva
        reserva.setEspacio(espacioRepository.findById(reservaEspacioDTO.getEspacioId())
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado")));
        reserva.setFecha(reservaEspacioDTO.getFecha());
        reserva.setHoraInicio(LocalTime.parse(reservaEspacioDTO.getHoraInicio()));
        reserva.setHoraFin(LocalTime.parse(reservaEspacioDTO.getHoraFin()));
        reserva.setMotivo(reservaEspacioDTO.getMotivo());
        reserva.setCurso(cursoRepository.findById(reservaEspacioDTO.getCursoId()).orElse(null));

        reservaEspacioRepository.save(reserva);
        return true;
    }

    public boolean verificarDisponibilidad(ReservaEspacioDTO reservaEspacioDTO) {
        Espacio espacio = espacioRepository.findById(reservaEspacioDTO.getEspacioId())
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        List<ReservaEspacio> reservas = reservaEspacioRepository.findByEspacioAndFecha(espacio, reservaEspacioDTO.getFecha());
        LocalTime horaInicio = LocalTime.parse(reservaEspacioDTO.getHoraInicio());
        LocalTime horaFin = LocalTime.parse(reservaEspacioDTO.getHoraFin());

        for (ReservaEspacio reserva : reservas) {
            if (horaInicio.isBefore(reserva.getHoraFin()) && horaFin.isAfter(reserva.getHoraInicio())) {
                return false; // Hay conflicto de horarios
            }
        }
        return true; // No hay conflicto
    }

}