package sistema.academico.entities;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cedula;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String apellido;
    private String direccion;

    @Column(nullable = false, unique = true)
    private String correo;

    private String telefono;
    @Column(nullable = false, unique = true)
    private String genero;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    private String codigo;
    @Column(nullable = false, unique = true)
    private String contrasena;
    private boolean estado;
    private String rol;
}