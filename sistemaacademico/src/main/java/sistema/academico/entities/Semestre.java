package sistema.academico.entities;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "semestre")
public class Semestre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private int numero;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "anio")
    private int anio;

    @OneToMany(mappedBy = "semestre")
    private List<Materia> materias;
}
