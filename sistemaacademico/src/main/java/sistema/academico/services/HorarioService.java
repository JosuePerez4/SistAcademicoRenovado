package sistema.academico.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import sistema.academico.DTO.HorarioGeneradoDTO;
import sistema.academico.DTO.HorarioRequestDTO;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Horario;
import sistema.academico.repository.CursoRepository;
import sistema.academico.repository.HorarioRepository;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public HorarioGeneradoDTO obtenerDetalles(Long horarioId) {
        Horario horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        return new HorarioGeneradoDTO(
                horario.getCurso().getNombre(),
                horario.getCurso().getCodigo(),
                horario.getAula(),
                horario.getDiaSemana(), // Aquí ya estamos usando String directamente
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
                    horario.getAula(),
                    horario.getDiaSemana(), // Aquí ya estamos usando String directamente
                    horario.getHoraInicio().toString(),
                    horario.getHoraFin().toString()));
        }

        return horariosDTO;
    }

    public HorarioGeneradoDTO agregarHorario(HorarioRequestDTO horarioRequestDTO) {
        Curso curso = cursoRepository.findById(horarioRequestDTO.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Horario nuevoHorario = new Horario();

        // Guardar el día de la semana en mayúsculas para asegurar la consistencia
        nuevoHorario.setDiaSemana(horarioRequestDTO.getDiaSemana().toUpperCase());
        nuevoHorario.setHoraInicio(LocalTime.parse(horarioRequestDTO.getHoraInicio()));
        nuevoHorario.setHoraFin(LocalTime.parse(horarioRequestDTO.getHoraFin()));
        nuevoHorario.setAula(horarioRequestDTO.getAula());
        nuevoHorario.setCurso(curso);

        if (validarSolapamiento(nuevoHorario)) {
            throw new RuntimeException("El horario se solapa con otro evento en la misma aula.");
        }

        if (!verificarDisponibilidadAula(nuevoHorario.getAula(), nuevoHorario.getDiaSemana(),
                nuevoHorario.getHoraInicio(), nuevoHorario.getHoraFin())) {
            throw new RuntimeException("La aula seleccionada no está disponible en el horario indicado.");
        }

        Horario horarioGuardado = horarioRepository.save(nuevoHorario);

        return new HorarioGeneradoDTO(
                curso.getNombre(),
                curso.getCodigo(),
                horarioGuardado.getAula(),
                horarioGuardado.getDiaSemana(), // Aquí ya estamos usando String directamente
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

        LocalTime horaInicio = LocalTime.parse(horarioRequestDTO.getHoraInicio());
        LocalTime horaFin = LocalTime.parse(horarioRequestDTO.getHoraFin());

        if (!verificarDisponibilidadAula(horarioRequestDTO.getAula(), horarioRequestDTO.getDiaSemana(),
                horaInicio, horaFin)) {
            throw new RuntimeException("La aula seleccionada no está disponible en el horario indicado.");
        }

        horarioExistente.setDiaSemana(horarioRequestDTO.getDiaSemana().toUpperCase()); // Guardar el día en mayúsculas
        horarioExistente.setHoraInicio(horaInicio);
        horarioExistente.setHoraFin(horaFin);
        horarioExistente.setAula(horarioRequestDTO.getAula());

        if (validarSolapamiento(horarioExistente)) {
            throw new RuntimeException("El horario se solapa con otro evento en la misma aula.");
        }

        Horario horarioActualizado = horarioRepository.save(horarioExistente);

        return new HorarioGeneradoDTO(
                horarioExistente.getCurso().getNombre(),
                horarioExistente.getCurso().getCodigo(),
                horarioActualizado.getAula(),
                horarioActualizado.getDiaSemana(), // Aquí ya estamos usando String directamente
                horarioActualizado.getHoraInicio().toString(),
                horarioActualizado.getHoraFin().toString());
    }

    private boolean validarSolapamiento(Horario nuevoHorario) {
        List<Horario> horariosExistentes = horarioRepository.findByAulaAndDiaSemana(
                nuevoHorario.getAula(), nuevoHorario.getDiaSemana());

        for (Horario existente : horariosExistentes) {
            if (!(nuevoHorario.getHoraFin().isBefore(existente.getHoraInicio()) ||
                    nuevoHorario.getHoraInicio().isAfter(existente.getHoraFin()))) {
                return true;
            }
        }
        return false;
    }

    private boolean verificarDisponibilidadAula(String aula, String diaSemana, LocalTime horaInicio,
            LocalTime horaFin) {
        List<Horario> horariosAula = horarioRepository.findByAulaAndDiaSemana(aula, diaSemana);
        for (Horario horario : horariosAula) {
            if (!(horaFin.isBefore(horario.getHoraInicio()) || horaInicio.isAfter(horario.getHoraFin()))) {
                return false;
            }
        }
        return true;
    }
}