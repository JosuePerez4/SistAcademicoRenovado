package sistema.academico.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recurso")
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo; // "Libro", "Equipo", etc.

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private boolean disponible;

    @Column(nullable = false)
    private String estado; // "Bueno", "Dañado", "En mantenimiento"
}
