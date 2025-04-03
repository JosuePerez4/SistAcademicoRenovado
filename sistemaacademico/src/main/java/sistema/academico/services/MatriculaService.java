package sistema.academico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Curso;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.Semestre;
import sistema.academico.repository.MatriculaRepository;

@Service
public class MatriculaService {
    @Autowired
    private MatriculaRepository matriculaRepository;

    // Corregir
    boolean registrarMatricula(Estudiante estudiante, Semestre semestre, List<Curso> cursos) {
        return false;
    }
    // Corregir
    boolean cancelarMatricula() {
        return false;
    }
    // Corregir
    List<Curso> listarCursosInscritos() {
        return null;
    }
}
