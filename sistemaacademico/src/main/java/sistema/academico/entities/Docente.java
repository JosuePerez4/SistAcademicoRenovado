package sistema.academico.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
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

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "cedula", unique = true)
    private String cedula;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @OneToMany(mappedBy = "docente")
    private List<Curso> cursos;

    

}


