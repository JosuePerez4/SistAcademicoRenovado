package sistema.academico.entities;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimiento_recurso")
public class MovimientoRecurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recurso_id", nullable = false)
    private Recurso recurso;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Estudiante, Docente o Administrativo

    @Column(name = "tipo_movimiento", nullable = false)
    private String tipoMovimiento; // "PRESTAMO", "DEVOLUCION", "MANTENIMIENTO"

    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDate fechaMovimiento;

    @Column(length = 500)
    private String observacion;
}
