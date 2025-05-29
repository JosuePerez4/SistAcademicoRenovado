package sistema.academico.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import sistema.academico.enums.TipoEspacio;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "espacio")
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_espacio")
    private TipoEspacio tipo;
    @Column(name = "capacidad")
    private Integer capacidad;
    @Column(name = "ubicacion")
    private String ubicacion;
    @Column(name = "disponible")
    private Boolean disponible;
    @OneToMany(mappedBy = "espacio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservaEspacio> reservasEspacio;
}
