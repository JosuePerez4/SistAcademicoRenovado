package sistema.academico.services;

import sistema.academico.DTO.*;

import java.util.List;

public interface RecursoService {
    RecursoDTO crearRecurso(RecursoRequestDTO dto);
    List<RecursoDTO> listarRecursos();
    MovimientoRecursoDTO registrarMovimiento(MovimientoRecursoDTO dto);
}
