package sistema.academico.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permiso")
@Getter
@Setter
@NoArgsConstructor
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @ManyToMany(mappedBy = "permisos")
    private Set<Rol> roles = new HashSet<>();

    public Permiso(String nombre) {
        this.nombre = nombre;
    }

    // Puedes agregar métodos para facilitar la adición y eliminación de roles si lo deseas
    public void agregarRol(Rol rol) {
        this.roles.add(rol);
        rol.getPermisos().add(this);
    }

    public void eliminarRol(Rol rol) {
        this.roles.remove(rol);
        rol.getPermisos().remove(this);
    }
}