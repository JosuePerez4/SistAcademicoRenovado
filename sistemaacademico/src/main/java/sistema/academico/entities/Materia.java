package sistema.academico.entities;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;
// YO
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private List<Materia> prerrequisitos;
    private String codigo;
    private String descripcion;
    private int creditos;
    private List<String> temario;
    private Curso curso;
}
