package sistema.academico.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cedula;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;
    private String direccion;

    @Column(nullable = false, unique = true)
    private String correo;

    private String telefono;
    @Column(nullable = false)
    private String genero;

    private LocalDate fechaNacimiento;

    private String codigo;
    @Column(nullable = false)
    private String contrasena;
    private boolean estado;
    
    @ManyToMany
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    // Métodos auxiliares para gestionar los roles
    public void agregarRol(Rol rol) {
        this.roles.add(rol);
        rol.getUsuarios().add(this);
    }

    public void eliminarRol(Rol rol) {
        this.roles.remove(rol);
        rol.getUsuarios().remove(this);
    }
}