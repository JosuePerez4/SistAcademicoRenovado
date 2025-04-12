package sistema.academico.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
import sistema.academico.enums.AsistenciaEstado;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private AsistenciaEstado estado; // Puedes cambiarlo por un ENUM si lo manejas así

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
}

