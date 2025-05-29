package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sistema.academico.entities.Curso;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.Evaluacion;
import sistema.academico.entities.Notificacion;
import sistema.academico.entities.Usuario;
import sistema.academico.repository.CursoRepository;
import sistema.academico.repository.EvaluacionRepository;
import sistema.academico.repository.NotificacionRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CursoRepository cursoRepository;

    /*
     * Crear una nueva notificación
     * public Notificacion crearNotificacion(String destinatario, Mensaje mensaje,
     * Usuario usuarioDestino) {
     * Notificacion notificacion = new Notificacion();
     * notificacion.setDestinatario(destinatario);
     * notificacion.setMensaje(mensaje);
     * notificacion.setUsuarioDestino(usuarioDestino);
     * notificacion.setFechaCreacion(new Date()); // Establecer fecha actual
     * return notificacionRepository.save(notificacion);
     * }
     */

    // Obtener todas las notificaciones
    public List<Notificacion> obtenerTodasNotificaciones() {
        return notificacionRepository.findAll();
    }

    // Enviar notificación (actualizar estado de envío)
    public boolean enviarNotificacion(Long idNotificacion) {
        Optional<Notificacion> notificacionOpt = notificacionRepository.findById(idNotificacion);
        if (notificacionOpt.isPresent()) {
            Notificacion notificacion = notificacionOpt.get();
            // Aquí agregarías la lógica real para enviar la notificación (por correo, SMS,
            // etc.)
        }
        return false;
    }

    // Eliminar notificación por ID
    public boolean eliminarNotificacion(Long idNotificacion) {
        Optional<Notificacion> notificacionOpt = notificacionRepository.findById(idNotificacion);
        if (notificacionOpt.isPresent()) {
            notificacionRepository.deleteById(idNotificacion);
            return true;
        }
        return false;
    }

    @Transactional
    @Scheduled(cron = "0 0 8 * * ?") // Se ejecuta todos los días a las 8 AM
    public void verificarEvaluacionesProximas() {
        LocalDate fechaHoy = LocalDate.now();
        LocalDate fechaLimiteEvaluacion = fechaHoy.plusDays(1); // Evaluaciones que ocurren en 1 día

        // Buscar evaluaciones cuya fecha límite es mañana
        List<Evaluacion> evaluaciones = evaluacionRepository.findByFechaLimite(fechaLimiteEvaluacion);

        // Enviar correos a los estudiantes y docentes de los cursos correspondientes
        for (Evaluacion evaluacion : evaluaciones) {
            enviarCorreoAEstudiantesYDocente(evaluacion);
        }
    }

    // Método para enviar correos a los estudiantes y al docente
    private void enviarCorreoAEstudiantesYDocente(Evaluacion evaluacion) {
        Curso curso = evaluacion.getCurso();

        // Obtener estudiantes del curso a través de la relación de Inscripción
        List<Estudiante> estudiantes = curso.getInscripciones().stream()
                .map(inscripcion -> inscripcion.getMatricula().getEstudiante()) // Obtener estudiante a través de
                                                                                // matrícula
                .collect(Collectors.toList());

        // Obtener el docente del curso verificando si existe buscandolo por el ID
        Usuario docenteOpt = cursoRepository.findDocenteById(curso.getId());
        Usuario docente = null;
        if (docenteOpt != null) {
            docente = docenteOpt;
        }

        String asunto = "Recordatorio: Evaluación próxima a realizarse";
        String mensaje = String.format(
                "Estimado/a, le recordamos que la evaluación %s de %s se realizará el %s a las %s.",
                evaluacion.getTipo(), curso.getNombre(), evaluacion.getFechaLimite(),
                obtenerHoraEvaluacion(evaluacion));

        // Enviar correo a los estudiantes
        for (Estudiante estudiante : estudiantes) {
            enviarCorreo(estudiante.getCorreo(), asunto, mensaje);
        }

        // Enviar correo al docente
        if (docente != null) {
            enviarCorreo(docente.getCorreo(), asunto, mensaje);
        }
    }

    // Método auxiliar para obtener la hora de la evaluación desde el horario
    private String obtenerHoraEvaluacion(Evaluacion evaluacion) {
        return evaluacion.getCurso().getHorarios().stream()
                .filter(horario -> horario.getEvaluacion().equals(evaluacion))
                .map(horario -> horario.getHoraInicio().toString())
                .findFirst()
                .orElse("Hora no definida");
    }

    // Método auxiliar para enviar el correo
    private void enviarCorreo(String destinatario, String asunto, String mensaje) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(destinatario);
        email.setSubject(asunto);
        email.setText(mensaje);

        // Enviar el correo
        mailSender.send(email);
    }
}