package sistema.academico.entities;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "programa_academico")
public class ProgramaAcademico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String codigo;
    private String nivel;
    private String modalidad;
    private int duracionSemestres;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private boolean estado;

    @OneToMany(mappedBy = "programaAcademico", cascade = CascadeType.ALL)
    private Set<Estudiante> estudiantes;
}
