package sistema.academico.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistema.academico.DTO.DocenteRegistroRequestDTO;
import sistema.academico.DTO.DocenteResponseDTO;
import sistema.academico.DTO.DocenteUpdateDTO;
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

    // Registro completo de docente (datos personales + profesionales)
    public DocenteResponseDTO registrarDocenteConUsuario(DocenteRegistroRequestDTO dto) {

        Docente docente = new Docente();
    
        docente.setEspecialidad(dto.getEspecialidad());
        docente.setCargaHoraria(dto.getCargaHoraria());
        docente.setTituloProfesional(dto.getTituloProfesional());
        docente.setAniosExperiencia(dto.getAniosExperiencia());
        docente.setTipoContrato(dto.getTipoContrato());
        docente.setCedula(dto.getCedula());
        docente.setNombre(dto.getNombre());
        docente.setApellido(dto.getApellido());
        docente.setDireccion(dto.getDireccion());
        docente.setCorreo(dto.getCorreo());
        docente.setTelefono(dto.getTelefono());
        docente.setGenero(dto.getGenero());
        docente.setFechaNacimiento(dto.getFechaNacimiento());
        docente.setCodigo(dto.getCodigo());
        docente.setContrasena(dto.getContrasena());
        docente.setEstado(dto.isEstado());
        docente.setRol(dto.getRol());

        Docente docenteGuardado = docenteRepository.save(docente);

        return mapToResponseDTO(docenteGuardado);
    }

    public DocenteResponseDTO actualizarDocente(Long id, DocenteUpdateDTO dto) {
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
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado con ID: " + id));
        return mapToResponseDTO(docente);
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

        // Datos personales (puedes omitirlos si no los necesitas)
        response.setNombre(docente.getNombre());
        response.setApellido(docente.getApellido());
        response.setCedula(docente.getCedula());
        response.setCorreo(docente.getCorreo());

        return response;
    }
}