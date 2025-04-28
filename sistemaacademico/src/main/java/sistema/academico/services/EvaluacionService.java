package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.academico.DTO.*;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Evaluacion;
import sistema.academico.entities.Horario;
import sistema.academico.entities.Espacio; // Importamos Espacio
import sistema.academico.enums.TipoEspacio;
import sistema.academico.repository.*;

import java.time.LocalDate;
import java.util.List;

@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ReservaEspacioRepository reservaEspacioRepository; // Para verificar las reservas de espacio

    @Autowired
    private EspacioRepository espacioRepository; // Para obtener los espacios disponibles

    // Método para crear evaluación con horario
    public EvaluacionResponseDTO crearEvaluacionConHorario(CrearEvaluacionDTO crearEvaluacionDTO) {
        // Verificar si ya existe una evaluación para el mismo tipo en ese curso
        Curso curso = cursoRepository.findById(crearEvaluacionDTO.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado."));

        // Verificar si ya existe una evaluación para el mismo tipo
        if (evaluacionRepository.existsByCursoAndTipo(curso, crearEvaluacionDTO.getTipo())) {
            throw new RuntimeException(
                    "Ya existe una evaluación de tipo " + crearEvaluacionDTO.getTipo() + " para este curso.");
        }

        // Crear la evaluación desde el DTO
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setTipo(crearEvaluacionDTO.getTipo());
        evaluacion.setDescripcion(crearEvaluacionDTO.getDescripcion());
        evaluacion.setFechaCreacion(LocalDate.now());
        evaluacion.setFechaLimite(crearEvaluacionDTO.getFechaLimite());
        evaluacion.setCurso(curso);

        // Guardamos la evaluación en la base de datos
        Evaluacion evaluacionGuardada = evaluacionRepository.save(evaluacion);

        // Crear el horario dependiendo del tipo de evaluación
        Horario horario = asignarHorarioDeEvaluacion(evaluacion);

        // Asignamos el horario a la evaluación
        horario.setCurso(evaluacion.getCurso());
        horario.setEvaluacion(evaluacion);

        // Guardamos el horario en la base de datos
        horarioRepository.save(horario);

        // Crear DTO de respuesta con el horario
        EvaluacionResponseDTO evaluacionResponseDTO = new EvaluacionResponseDTO();
        evaluacionResponseDTO.setId(evaluacionGuardada.getId());
        evaluacionResponseDTO.setTipo(evaluacionGuardada.getTipo());
        evaluacionResponseDTO.setDescripcion(evaluacionGuardada.getDescripcion());
        evaluacionResponseDTO.setFechaCreacion(evaluacionGuardada.getFechaCreacion());
        evaluacionResponseDTO.setFechaLimite(evaluacionGuardada.getFechaLimite());

        // Crear DTO para el horario asignado
        HorarioDTO horarioDTO = new HorarioDTO();
        horarioDTO.setDiaSemana(horario.getDiaSemana());
        horarioDTO.setHoraInicio(horario.getHoraInicio());
        horarioDTO.setHoraFin(horario.getHoraFin());
        // Asignar el aula al DTO de horario, verificar si el tipo es AULA y no laboratorio
        if (horario.getEspacio().getTipo() == TipoEspacio.AULA) {
            horarioDTO.setAula(horario.getEspacio().getNombre());
        } else {
            throw new RuntimeException("No se puede asignar un laboratorio como aula para la evaluación.");
        }

        // Incluir el horario asignado en la respuesta
        evaluacionResponseDTO.setHorarioAsignado(horarioDTO.getDiaSemana() + " " +
                horarioDTO.getHoraInicio() + "-" + horarioDTO.getHoraFin() + " en " + horarioDTO.getAula());

        return evaluacionResponseDTO;
    }

    // Método para asignar un horario a la evaluación, preferentemente de 2 horas
    private Horario asignarHorarioDeEvaluacion(Evaluacion evaluacion) {
        Curso curso = evaluacion.getCurso();
        // Obtener los horarios disponibles del curso
        List<Horario> horariosDisponibles = curso.getHorarios();

        // Filtrar los horarios disponibles para la evaluación
        horariosDisponibles.removeIf(horario -> horario.getEvaluacion() != null);
        if (horariosDisponibles.isEmpty()) {
            throw new RuntimeException("No hay horarios disponibles para asignar a la evaluación.");
        }

        // Buscar un horario de 2 horas de duración
        for (Horario horario : horariosDisponibles) {
            if (horaDuracionEsDosHoras(horario) && horario.getEspacio().getTipo() == TipoEspacio.AULA) {
                // Si encuentra un horario de 2 horas y tiene espacio tipo AULA, asigna el horario
                return horario;
            }
        }

        // Si no hay horarios de 2 horas, asignamos el primer horario disponible de 1 hora
        for (Horario horario : horariosDisponibles) {
            if (horaDuracionEsUnaHora(horario) && horario.getEspacio().getTipo() == TipoEspacio.AULA) {
                return horario;
            }
        }

        // Si no hay horarios disponibles, lanzar una excepción o manejar el error
        throw new RuntimeException("No hay horarios disponibles para asignar a la evaluación.");
    }

    // Método para verificar si el horario tiene una duración de 2 horas
    private boolean horaDuracionEsDosHoras(Horario horario) {
        return horario.getHoraFin().minusHours(horario.getHoraInicio().getHour()).getHour() == 2;
    }

    // Método para verificar si el horario tiene una duración de 1 hora
    private boolean horaDuracionEsUnaHora(Horario horario) {
        return horario.getHoraFin().minusHours(horario.getHoraInicio().getHour()).getHour() == 1;
    }
}
