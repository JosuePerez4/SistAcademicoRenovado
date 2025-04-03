package sistema.academico.entities;
import jakarta.persistence.*;
import lombok.*;
// YO
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Docente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String especialidad;
    private int cargaHoraria;
    private String tituloProfesional;
    private String aniosExperiencia;
    private String tipoContrato;
    private Curso curso;
}

