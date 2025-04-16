package sistema.academico.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Table(name = "docente")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Docente extends Usuario {

    @Column(name = "especialidad", nullable = false)
    private String especialidad;

    @Column(name = "carga_horaria")
    private int cargaHoraria;

    @Column(name = "titulo_profesional")
    private String tituloProfesional;

    @Column(name = "anios_experiencia")
    private String aniosExperiencia;

    @Column(name = "tipo_contrato")
    private String tipoContrato;

    @OneToMany(mappedBy = "docente")
    private List<Curso> cursos;

    

}


