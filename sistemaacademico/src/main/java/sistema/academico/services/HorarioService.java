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
import sistema.academico.entities.Inscripcion;
import sistema.academico.entities.Matricula;
import sistema.academico.enums.EstadoCurso;
import sistema.academico.enums.EstadoInscripcion;
import sistema.academico.repository.CursoRepository;
import sistema.academico.repository.HorarioRepository;
import sistema.academico.repository.InscripcionRepository;
import sistema.academico.repository.MatriculaRepository;

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

    public List<HorarioGeneradoDTO> generarHorario(Long matriculaId) {
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));

        int semestreEstudiante = matricula.getSemestre().getNumero();

        List<Curso> cursosDisponibles = cursoRepository.findBySemestreAndEstadoCurso(semestreEstudiante,
                EstadoCurso.ABIERTO);

        List<HorarioGeneradoDTO> resultado = new ArrayList<>();
        int creditosActuales = 0;
        List<Horario> horariosActuales = new ArrayList<>();

        for (Curso curso : cursosDisponibles) {
            if (creditosActuales >= 21) break;

            int inscritos = curso.getInscripciones().size();
            if (inscritos >= curso.getCupoMaximo()) continue;

            int creditosCurso = curso.getMateria().getCreditos();
            if (creditosActuales + creditosCurso > 21) continue;

            boolean solapado = false;
            for (Horario nuevoHorario : curso.getHorarios()) {
                for (Horario horarioExistente : horariosActuales) {
                    if (nuevoHorario.getDiaSemana().equals(horarioExistente.getDiaSemana()) &&
                        !(nuevoHorario.getHoraFin().isBefore(horarioExistente.getHoraInicio()) ||
                          nuevoHorario.getHoraInicio().isAfter(horarioExistente.getHoraFin()))) {
                        solapado = true;
                        break;
                    }
                }
                if (solapado) break;
            }

            if (!solapado) {
                Inscripcion nuevaInscripcion = new Inscripcion();
                nuevaInscripcion.setCurso(curso);
                nuevaInscripcion.setMatricula(matricula);
                nuevaInscripcion.setFechaInscripcion(LocalDate.now());
                nuevaInscripcion.setEstado(EstadoInscripcion.INSCRITO);
                nuevaInscripcion.setNotaFinal(null);
                nuevaInscripcion.setAsistencias(null);
                inscripcionRepository.save(nuevaInscripcion);

                creditosActuales += creditosCurso;
                horariosActuales.addAll(curso.getHorarios());

                for (Horario horario : curso.getHorarios()) {
                    resultado.add(new HorarioGeneradoDTO(
                            curso.getNombre(),
                            curso.getCodigo(),
                            horario.getAula(),
                            horario.getDiaSemana(),
                            horario.getHoraInicio(),
                            horario.getHoraFin()));
                }
            }
        }

        return resultado;
    }

    public void actualizarHorario(String diaSemana, java.sql.Time horaInicio, String aula,
            String nuevoDia, java.sql.Time nuevaHoraInicio, java.sql.Time nuevaHoraFin, String nuevaAula) {
        // Método vacío para compatibilidad (puedes implementar si lo necesitas)
    }

    public String obtenerDetalles(Horario horario) {
        return null; // Implementa si necesitas mostrar detalles formateados
    }

    public List<HorarioGeneradoDTO> listarHorariosPorCurso(Long cursoId) {
        List<Horario> horarios = horarioRepository.findByCursoId(cursoId);
        List<HorarioGeneradoDTO> horariosDTO = new ArrayList<>();

        for (Horario horario : horarios) {
            horariosDTO.add(new HorarioGeneradoDTO(
                    horario.getCurso().getNombre(),
                    horario.getCurso().getCodigo(),
                    horario.getAula(),
                    horario.getDiaSemana(),
                    horario.getHoraInicio(),
                    horario.getHoraFin()));
        }

        return horariosDTO;
    }

    public HorarioGeneradoDTO agregarHorario(HorarioRequestDTO horarioRequestDTO) {
        Curso curso = cursoRepository.findById(horarioRequestDTO.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Horario nuevoHorario = new Horario();
        nuevoHorario.setDiaSemana(horarioRequestDTO.getDiaSemana());
        nuevoHorario.setHoraInicio(horarioRequestDTO.getHoraInicio());
        nuevoHorario.setHoraFin(horarioRequestDTO.getHoraFin());
        nuevoHorario.setAula(horarioRequestDTO.getAula());
        nuevoHorario.setCurso(curso);

        if (validarSolapamiento(nuevoHorario)) {
            throw new RuntimeException("El horario se solapa con otro evento en la misma aula.");
        }

        if (!verificarDisponibilidadAula(nuevoHorario.getAula(), nuevoHorario.getHoraInicio(),
                nuevoHorario.getHoraFin())) {
            throw new RuntimeException("La aula seleccionada no está disponible en el horario indicado.");
        }

        Horario horarioGuardado = horarioRepository.save(nuevoHorario);

        return new HorarioGeneradoDTO(
                curso.getNombre(),
                curso.getCodigo(),
                horarioGuardado.getAula(),
                horarioGuardado.getDiaSemana(),
                horarioGuardado.getHoraInicio(),
                horarioGuardado.getHoraFin());
    }

    public void eliminarHorario(Long horarioId) {
        Horario horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        horarioRepository.delete(horario);
    }

    public HorarioGeneradoDTO actualizarHorario(Long horarioId, HorarioRequestDTO horarioRequestDTO) {
        Horario horarioExistente = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        LocalTime horaInicio = horarioRequestDTO.getHoraInicio();
        LocalTime horaFin = horarioRequestDTO.getHoraFin();

        if (!verificarDisponibilidadAula(horarioRequestDTO.getAula(), horaInicio, horaFin)) {
            throw new RuntimeException("La aula seleccionada no está disponible en el horario indicado.");
        }

        Horario nuevoHorario = new Horario();
        nuevoHorario.setDiaSemana(horarioRequestDTO.getDiaSemana());
        nuevoHorario.setHoraInicio(horaInicio);
        nuevoHorario.setHoraFin(horaFin);
        nuevoHorario.setAula(horarioRequestDTO.getAula());

        if (validarSolapamiento(nuevoHorario)) {
            throw new RuntimeException("El horario se solapa con otro evento en la misma aula.");
        }

        horarioExistente.setDiaSemana(horarioRequestDTO.getDiaSemana());
        horarioExistente.setHoraInicio(horaInicio);
        horarioExistente.setHoraFin(horaFin);
        horarioExistente.setAula(horarioRequestDTO.getAula());

        Horario horarioActualizado = horarioRepository.save(horarioExistente);

        return new HorarioGeneradoDTO(
                horarioExistente.getCurso().getNombre(),
                horarioExistente.getCurso().getCodigo(),
                horarioActualizado.getAula(),
                horarioActualizado.getDiaSemana(),
                horarioActualizado.getHoraInicio(),
                horarioActualizado.getHoraFin());
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

    private boolean verificarDisponibilidadAula(String aula, LocalTime horaInicio, LocalTime horaFin) {
        List<Horario> horariosAula = horarioRepository.findByAula(aula);
        for (Horario horario : horariosAula) {
            if (!(horaFin.isBefore(horario.getHoraInicio()) || horaInicio.isAfter(horario.getHoraFin()))) {
                return false;
            }
        }
        return true;
    }
}
