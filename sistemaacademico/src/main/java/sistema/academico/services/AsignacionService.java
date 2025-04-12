package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.academico.entities.*;
import sistema.academico.repository.*;

import java.util.List;

@Service
public class AsignacionService {

    private final CursoRepository cursoRepository;
    private final DocenteRepository docenteRepository;

    @Autowired
    public AsignacionService(CursoRepository cursoRepository, DocenteRepository docenteRepository) {
        this.cursoRepository = cursoRepository;
        this.docenteRepository = docenteRepository;
    }

    public void asignarCursosAutomaticamente() {
        List<Curso> cursosSinDocente = cursoRepository.findByDocenteIsNull();
        List<Docente> docentes = docenteRepository.findAll();

        for (Curso curso : cursosSinDocente) {
            for (Docente docente : docentes) {
                if (puedeAsignarse(docente, curso)) {
                    curso.setDocente(docente);
                    docente.getCursos().add(curso);
                    docente.setCargaHoraria(docente.getCargaHoraria() + curso.getMateria().getHoras());
                    cursoRepository.save(curso);
                    break;
                }
            }
        }
    }

    private boolean puedeAsignarse(Docente docente, Curso curso) {
        int horasTotales = docente.getCargaHoraria() + curso.getMateria().getHoras();
        if (horasTotales > 40)
            return false; // carga horaria máxima

        for (Curso asignado : docente.getCursos()) {
            for (Horario hExistente : asignado.getHorarios()) {
                for (Horario hNuevo : curso.getHorarios()) {
                    if (conflictoHorario(hExistente, hNuevo)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean conflictoHorario(Horario h1, Horario h2) {
        if (!h1.getDiaSemana().equalsIgnoreCase(h2.getDiaSemana()))
            return false;
        return h1.getHoraInicio().before(h2.getHoraFin()) && h2.getHoraInicio().before(h1.getHoraFin());
    }
}
