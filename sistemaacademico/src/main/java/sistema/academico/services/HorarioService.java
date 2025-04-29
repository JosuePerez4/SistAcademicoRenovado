package sistema.academico.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.DTO.HorarioGeneradoDTO;
import sistema.academico.DTO.HorarioRequestDTO;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Espacio;
import sistema.academico.entities.Horario;
import sistema.academico.entities.Inscripcion;
import sistema.academico.entities.Matricula;
import sistema.academico.enums.EstadoCurso;
import sistema.academico.enums.EstadoInscripcion;
import sistema.academico.repository.*;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private EspacioRepository espacioRepository;

    public HorarioGeneradoDTO obtenerDetalles(Long horarioId) {
        Horario horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        return new HorarioGeneradoDTO(
                horario.getCurso().getNombre(),
                horario.getCurso().getCodigo(),
                horario.getEspacio().getNombre(),
                horario.getDiaSemana(),
                horario.getHoraInicio().toString(),
                horario.getHoraFin().toString());
    }

    public List<HorarioGeneradoDTO> listarHorariosPorCurso(Long cursoId) {
        List<Horario> horarios = horarioRepository.findByCursoId(cursoId);
        List<HorarioGeneradoDTO> horariosDTO = new ArrayList<>();

        for (Horario horario : horarios) {
            horariosDTO.add(new HorarioGeneradoDTO(
                    horario.getCurso().getNombre(),
                    horario.getCurso().getCodigo(),
                    horario.getEspacio().getNombre(),
                    horario.getDiaSemana(),
                    horario.getHoraInicio().toString(),
                    horario.getHoraFin().toString()));
        }

        return horariosDTO;
    }

    public HorarioGeneradoDTO agregarHorario(HorarioRequestDTO horarioRequestDTO) {
        Curso curso = cursoRepository.findById(horarioRequestDTO.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Espacio espacio = espacioRepository.findById(horarioRequestDTO.getEspacioId())
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        Horario nuevoHorario = new Horario();
        nuevoHorario.setDiaSemana(horarioRequestDTO.getDiaSemana().toUpperCase());
        nuevoHorario.setHoraInicio(LocalTime.parse(horarioRequestDTO.getHoraInicio()));
        nuevoHorario.setHoraFin(LocalTime.parse(horarioRequestDTO.getHoraFin()));
        nuevoHorario.setEspacio(espacio);
        nuevoHorario.setCurso(curso);

        if (validarSolapamiento(nuevoHorario)) {
            throw new RuntimeException("El horario se solapa con otro evento en el mismo espacio.");
        }

        Horario horarioGuardado = horarioRepository.save(nuevoHorario);

        return new HorarioGeneradoDTO(
                curso.getNombre(),
                curso.getCodigo(),
                espacio.getNombre(),
                horarioGuardado.getDiaSemana(),
                horarioGuardado.getHoraInicio().toString(),
                horarioGuardado.getHoraFin().toString());
    }

    public void eliminarHorario(Long horarioId) {
        Horario horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        horarioRepository.delete(horario);
    }

    public HorarioGeneradoDTO actualizarHorario(Long horarioId, HorarioRequestDTO horarioRequestDTO) {
        Horario horarioExistente = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        Espacio espacio = espacioRepository.findById(horarioRequestDTO.getEspacioId())
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        horarioExistente.setDiaSemana(horarioRequestDTO.getDiaSemana().toUpperCase());
        horarioExistente.setHoraInicio(LocalTime.parse(horarioRequestDTO.getHoraInicio()));
        horarioExistente.setHoraFin(LocalTime.parse(horarioRequestDTO.getHoraFin()));
        horarioExistente.setEspacio(espacio);

        if (validarSolapamiento(horarioExistente)) {
            throw new RuntimeException("El horario actualizado se solapa con otro evento.");
        }

        Horario horarioActualizado = horarioRepository.save(horarioExistente);

        return new HorarioGeneradoDTO(
                horarioActualizado.getCurso().getNombre(),
                horarioActualizado.getCurso().getCodigo(),
                horarioActualizado.getEspacio().getNombre(),
                horarioActualizado.getDiaSemana(),
                horarioActualizado.getHoraInicio().toString(),
                horarioActualizado.getHoraFin().toString());
    }

    private boolean validarSolapamiento(Horario nuevoHorario) {
        List<Horario> horariosExistentes = horarioRepository.findByEspacioAndDiaSemana(
                nuevoHorario.getEspacio(), nuevoHorario.getDiaSemana());

        for (Horario existente : horariosExistentes) {
            if (nuevoHorario.getHoraInicio().isBefore(existente.getHoraFin()) &&
                    nuevoHorario.getHoraFin().isAfter(existente.getHoraInicio())) {
                return true;
            }
        }
        return false;
    }
}
