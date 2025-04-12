package sistema.academico.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistema.academico.DTO.DocenteRequestDTO;
import sistema.academico.DTO.DocenteResponseDTO;
import sistema.academico.entities.Docente;
import sistema.academico.entities.Usuario;
import sistema.academico.repository.DocenteRepository;
import sistema.academico.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocenteService {

    private final DocenteRepository docenteRepository;
    private final UsuarioRepository usuarioRepository;

    public DocenteResponseDTO registrarDocente(Long usuarioId, DocenteRequestDTO dto) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioId);
        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }

        Usuario usuario = optionalUsuario.get();

        Docente docente = new Docente();
        docente.setId(usuario.getId()); // Hereda el ID del usuario
        docente.setEspecialidad(dto.getEspecialidad());
        docente.setCargaHoraria(dto.getCargaHoraria());
        docente.setTituloProfesional(dto.getTituloProfesional());
        docente.setAniosExperiencia(dto.getAniosExperiencia());
        docente.setTipoContrato(dto.getTipoContrato());

        Docente guardado = docenteRepository.save(docente);

        return mapToResponseDTO(guardado);
    }

    public DocenteResponseDTO actualizarDocente(Long id, DocenteRequestDTO dto) {
        Optional<Docente> optional = docenteRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Docente no encontrado con ID: " + id);
        }

        Docente docente = optional.get();
        docente.setEspecialidad(dto.getEspecialidad());
        docente.setCargaHoraria(dto.getCargaHoraria());
        docente.setTituloProfesional(dto.getTituloProfesional());
        docente.setAniosExperiencia(dto.getAniosExperiencia());
        docente.setTipoContrato(dto.getTipoContrato());

        Docente actualizado = docenteRepository.save(docente);

        return mapToResponseDTO(actualizado);
    }

    public void eliminarDocente(Long id) {
        if (!docenteRepository.existsById(id)) {
            throw new RuntimeException("Docente no encontrado con ID: " + id);
        }
        docenteRepository.deleteById(id);
    }

    public DocenteResponseDTO obtenerDocentePorId(Long id) {
        Optional<Docente> optional = docenteRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Docente no encontrado con ID: " + id);
        }
        return mapToResponseDTO(optional.get());
    }

    public List<DocenteResponseDTO> obtenerTodosLosDocentes() {
        List<Docente> docentes = docenteRepository.findAll();
        return docentes.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private DocenteResponseDTO mapToResponseDTO(Docente docente) {
        DocenteResponseDTO response = new DocenteResponseDTO();
        response.setId(docente.getId());
        response.setEspecialidad(docente.getEspecialidad());
        response.setCargaHoraria(docente.getCargaHoraria());
        response.setTituloProfesional(docente.getTituloProfesional());
        response.setAniosExperiencia(docente.getAniosExperiencia());
        response.setTipoContrato(docente.getTipoContrato());
        return response;
    }
}
