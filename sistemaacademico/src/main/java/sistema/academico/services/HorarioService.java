package sistema.academico.services;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Horario;
import sistema.academico.repository.HorarioRepository;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    public String generarHorario() {
        return null;
    }

    // Validar si hay solapamiento con otro horario en la misma aula
    public boolean validarSolapamiento(Horario nuevoHorario) {
        return false;
    }

    // Actualizar un horario existente
    public void actualizarHorario(String diaSemana, Time horaInicio, String aula,
            String nuevoDia, Time nuevaHoraInicio, Time nuevaHoraFin, String nuevaAula) {
    }

    // Obtener detalles del horario en formato de texto
    public String obtenerDetalles(Horario horario) {
        return null;
    }
}