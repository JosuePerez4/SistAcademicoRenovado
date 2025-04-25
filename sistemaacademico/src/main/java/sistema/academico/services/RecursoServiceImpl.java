package sistema.academico.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistema.academico.DTO.*;
import sistema.academico.entities.*;
import sistema.academico.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecursoServiceImpl implements RecursoService {

    private final RecursoRepository recursoRepository;
    private final MovimientoRecursoRepository movimientoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public RecursoDTO crearRecurso(RecursoRequestDTO dto) {
        Recurso recurso = new Recurso();
        recurso.setNombre(dto.getNombre());
        recurso.setTipo(dto.getTipo());
        recurso.setDescripcion(dto.getDescripcion());
        recurso.setEstado(dto.getEstado());
        recurso.setDisponible(true);
        recursoRepository.save(recurso);
        return toDTO(recurso);
    }

    @Override
    public List<RecursoDTO> listarRecursos() {
        return recursoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoRecursoDTO registrarMovimiento(MovimientoRecursoDTO dto) {
        Recurso recurso = recursoRepository.findById(dto.getRecursoId())
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validaciones según tipo de movimiento
        switch (dto.getTipoMovimiento().toUpperCase()) {
            case "PRESTAMO":
                if (!recurso.isDisponible()) {
                    throw new RuntimeException("Recurso no disponible para préstamo");
                }
                recurso.setDisponible(false);
                break;
            case "DEVOLUCION":
                recurso.setDisponible(true);
                break;
            case "MANTENIMIENTO":
                recurso.setEstado("En mantenimiento");
                recurso.setDisponible(false);
                break;
            default:
                throw new RuntimeException("Tipo de movimiento inválido");
        }

        recursoRepository.save(recurso);

        MovimientoRecurso movimiento = new MovimientoRecurso();
        movimiento.setRecurso(recurso);
        movimiento.setUsuario(usuario);
        movimiento.setTipoMovimiento(dto.getTipoMovimiento().toUpperCase());
        movimiento.setObservacion(dto.getObservacion());
        movimiento.setFechaMovimiento(LocalDate.now());

        movimientoRepository.save(movimiento);

        dto.setFechaMovimiento(movimiento.getFechaMovimiento());
        return dto;
    }

    private RecursoDTO toDTO(Recurso recurso) {
        return new RecursoDTO(
                recurso.getId(),
                recurso.getNombre(),
                recurso.getTipo(),
                recurso.getDescripcion(),
                recurso.isDisponible(),
                recurso.getEstado()
        );
    }
}
