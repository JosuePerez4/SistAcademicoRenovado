package sistema.academico.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistema.academico.DTO.ComentarioRequestDTO;
import sistema.academico.DTO.ComentarioResponseDTO;
import sistema.academico.entities.ComentarioEvaluacion;
import sistema.academico.entities.Evaluacion;
import sistema.academico.entities.Usuario;
import sistema.academico.repository.ComentarioEvaluacionRepository;
import sistema.academico.repository.EvaluacionRepository;
import sistema.academico.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioEvaluacionService {

    private final ComentarioEvaluacionRepository comentarioRepo;
    private final UsuarioRepository usuarioRepo;
    private final EvaluacionRepository evaluacionRepo;

    public ComentarioResponseDTO agregarComentario(ComentarioRequestDTO dto) {
        Usuario autor = usuarioRepo.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        Evaluacion evaluacion = evaluacionRepo.findById(dto.getEvaluacionId())
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));

        ComentarioEvaluacion comentario = new ComentarioEvaluacion();
        comentario.setAutor(autor);
        comentario.setEvaluacion(evaluacion);
        comentario.setContenido(dto.getContenido());
        comentario.setFecha(LocalDateTime.now());

        comentarioRepo.save(comentario);

        return mapToDTO(comentario);
    }

    public List<ComentarioResponseDTO> listarComentarios(Long evaluacionId) {
        return comentarioRepo.findByEvaluacionId(evaluacionId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ComentarioResponseDTO mapToDTO(ComentarioEvaluacion c) {
        ComentarioResponseDTO dto = new ComentarioResponseDTO();
        dto.setId(c.getId());
        dto.setContenido(c.getContenido());
        dto.setFecha(c.getFecha());
        dto.setNombreAutor(c.getAutor().getNombre());
        return dto;
    }
}
