
package sistema.academico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.DTO.CrearCursoRequestDTO;
import sistema.academico.DTO.ListarCursoPorMateriaDTO;
import sistema.academico.DTO.ListarHorarioPorCursoDTO;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Docente;
import sistema.academico.entities.Horario;
import sistema.academico.entities.Materia;
import sistema.academico.repository.*;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    public CrearCursoRequestDTO crearCurso(CrearCursoRequestDTO curso) {
        Curso nuevoCurso = new Curso();
        nuevoCurso.setNombre(curso.getNombre());
        nuevoCurso.setDescripcion(curso.getDescripcion());
        nuevoCurso.setCodigo(curso.getCodigo());
        nuevoCurso.setGrupo(curso.getGrupo());
        nuevoCurso.setCupoMaximo(curso.getCupoMaximo());
        nuevoCurso.setSemestre(curso.getSemestre());
        nuevoCurso.setEstadoCurso(curso.getEstadoCurso());

        // Asignar el profesor al curso
        Docente docenteExistente = docenteRepository.findById(curso.getDocenteId()).orElse(null);
        if (docenteExistente != null) {
            nuevoCurso.setDocente(docenteExistente);
        } else {
            // Manejar el caso en que el docente no existe
            throw new RuntimeException("Docente no encontrado");
        }

        // Verificar que el curso

        // Asignar la materia al curso
        Materia materiaExistente = materiaRepository.findById(curso.getMateriaId()).orElse(null);
        if (materiaExistente != null) {
            nuevoCurso.setMateria(materiaExistente); // Esta línea debería funcionar sin problemas.
        } else {
            throw new RuntimeException("Materia no encontrada");
        }

        cursoRepository.save(nuevoCurso);

        return curso;
    }

    public CrearCursoRequestDTO modificarCurso(CrearCursoRequestDTO curso, long cursoId) {
        Curso cursoExistente = cursoRepository.findById(cursoId).orElse(null);
        if (cursoExistente != null) {
            cursoExistente.setNombre(curso.getNombre());
            cursoExistente.setDescripcion(curso.getDescripcion());
            cursoExistente.setCodigo(curso.getCodigo());
            cursoExistente.setGrupo(curso.getGrupo());
            cursoExistente.setCupoMaximo(curso.getCupoMaximo());
            cursoExistente.setSemestre(curso.getSemestre());
            cursoExistente.setEstadoCurso(curso.getEstadoCurso());
            // Asignar el profesor al curso
            Docente docenteExistente = docenteRepository.findById(curso.getDocenteId()).orElse(null);
            if (docenteExistente != null) {
                cursoExistente.setDocente(docenteExistente);
            } else {
                // Manejar el caso en que el docente no existe
                throw new RuntimeException("Docente no encontrado");
            }
            // Asignar la materia al curso
            Materia materiaExistente = materiaRepository.findById(curso.getMateriaId()).orElse(null);
            if (materiaExistente != null) {
                cursoExistente.setMateria(materiaExistente);
            } else {
                // Manejar el caso en que la materia no existe
                throw new RuntimeException("Materia no encontrada");
            }

            cursoRepository.save(cursoExistente);
        }
        return curso;
    }

    public String eliminarCurso(Long id) {
        Curso cursoExistente = cursoRepository.findById(id).orElse(null);
        if (cursoExistente != null) {
            cursoRepository.delete(cursoExistente);
            return "Curso eliminado con éxito";
        } else {
            return "Curso no encontrado";
        }
    }

    public List<ListarCursoPorMateriaDTO> listarCursosPorMateria(Long materiaId) {
        List<Curso> cursos = cursoRepository.findByMateriaId(materiaId);

        return cursos.stream().map(curso -> {
            ListarCursoPorMateriaDTO dto = new ListarCursoPorMateriaDTO();
            dto.setNombre(curso.getNombre());
            dto.setMateriaId(curso.getMateria().getId());
            dto.setGrupo(curso.getGrupo());
            dto.setCupoMaximo(curso.getCupoMaximo());
            dto.setDocenteId(curso.getDocente().getId());
            dto.setEstadoCurso(curso.getEstadoCurso());
            return dto;
        }).toList();
    }

    public List<ListarCursoPorMateriaDTO> listarTodosLosCursos() {
        List<Curso> cursos = cursoRepository.findAll();

        return cursos.stream().map(curso -> {
            ListarCursoPorMateriaDTO dto = new ListarCursoPorMateriaDTO();
            dto.setNombre(curso.getNombre());
            dto.setMateriaId(curso.getMateria().getId());
            dto.setGrupo(curso.getGrupo());
            dto.setCupoMaximo(curso.getCupoMaximo());
            dto.setDocenteId(curso.getDocente().getId());
            dto.setEstadoCurso(curso.getEstadoCurso());
            return dto;
        }).toList();
    }

    public List<ListarHorarioPorCursoDTO> listarHorarios(Long idCurso) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        List<ListarHorarioPorCursoDTO> horariosDTO = new ArrayList<>();
        if (cursoOpt.isPresent()) {
            Curso curso = cursoOpt.get();
            List<Horario> horarios = curso.getHorarios();
            for (Horario horario : horarios) {
                ListarHorarioPorCursoDTO horarioDTO = new ListarHorarioPorCursoDTO();
                horarioDTO.setDiaSemana(horario.getDiaSemana());
                horarioDTO.setHoraInicio(horario.getHoraInicio().toString());
                horarioDTO.setHoraFin(horario.getHoraFin().toString());
                horarioDTO.setAula(horario.getAula());
                horariosDTO.add(horarioDTO);
            }
        } else {
            // Manejar el caso en que el curso no existe
            throw new RuntimeException("Curso no encontrado");
        }
        return horariosDTO;
    }
}