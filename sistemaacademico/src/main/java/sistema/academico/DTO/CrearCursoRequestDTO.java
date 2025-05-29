package sistema.academico.DTO;

import lombok.*;
import sistema.academico.enums.EstadoCurso;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Nos sirve para crear un curso y para responder al cliente que creamos
public class CrearCursoRequestDTO {
    private String nombre;
    private String descripcion;
    private String codigo;
    private EstadoCurso estadoCurso;
    private String grupo;
    private int cupoMaximo;
    private int semestre;
    private Long docenteId;
    private Long materiaId;
}
