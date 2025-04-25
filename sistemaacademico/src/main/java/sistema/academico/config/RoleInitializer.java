package sistema.academico.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sistema.academico.entities.Rol;
import sistema.academico.repository.RolRepository;

@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        crearRolSiNoExiste("ADMINISTRADOR");
        crearRolSiNoExiste("DOCENTE");
        crearRolSiNoExiste("ESTUDIANTE");
    }

    private void crearRolSiNoExiste(String nombreRol) {
        if (!rolRepository.findByNombre(nombreRol).isPresent()) {
            Rol rol = new Rol(nombreRol);
            rolRepository.save(rol);
            System.out.println("Rol '" + nombreRol + "' creado.");
        }
    }
}